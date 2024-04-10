package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.RefreshTokenRepository;
import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.request.LoginUserRequestDto;
import by.bsuir.dorm.dto.request.RefreshTokenRequestDto;
import by.bsuir.dorm.dto.response.AccessResponseDto;
import by.bsuir.dorm.exception.CookieNotFoundException;
import by.bsuir.dorm.exception.InvalidTokenException;
import by.bsuir.dorm.exception.LoginException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.model.entity.RefreshToken;
import by.bsuir.dorm.model.entity.RefreshTokenKey;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.service.AuthService;
import by.bsuir.dorm.service.jwt.AccessJwtService;
import by.bsuir.dorm.service.jwt.RefreshJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final String COOKIE_REFRESH_TOKEN = "refresh-token";
    private static final String COOKIE_REFRESH_PATH = "/api/v1/auth";
    private static final String COOKIE_ATTR_EXPIRES = "Expires";
    private static final DateTimeFormatter expiresCookieFormatter =
            DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AccessJwtService accessJwtService;
    private final RefreshJwtService refreshJwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AccessResponseDto login(LoginUserRequestDto dto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        deleteRefreshTokenCookieIfPresent(request, response);
        User user = userRepository.findByCardId(dto.cardId())
                .orElseThrow(() -> new UserNotFoundException("User { cardId = " + dto.cardId() + " } doesn't exist"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            dto.password()
                    )
            );
        } catch (AuthenticationException ex) {
           throw new LoginException("Authentication failed", ex);
        }
        final String accessToken = accessJwtService.createToken(accessJwtService.getClaims(user));
        final UUID sessionId = UUID.randomUUID();
        final Claims refreshClaims = refreshJwtService.getClaims(user, sessionId);
        final String refreshToken = refreshJwtService.createToken(refreshClaims);
        final Instant refreshTokenExpirationTime = refreshClaims.getExpiration().toInstant();
        final RefreshTokenKey refreshTokenKey = new RefreshTokenKey(user, sessionId);
        final RefreshToken refreshTokenEntity = new RefreshToken(refreshTokenKey, refreshTokenExpirationTime);
        refreshTokenRepository.saveAndFlush(refreshTokenEntity);
        final Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_REFRESH_PATH);
        cookie.setAttribute(COOKIE_ATTR_EXPIRES, expiresCookieFormatter.format(refreshTokenExpirationTime));
        response.addCookie(cookie);
        log.info("User { id = " + user.getId() + ", cardId = " + user.getCardId() + " } logged in");
        return new AccessResponseDto(accessToken, user.getEmailConfirmed(), user.getPasswordNeedReset());
    }

    @Override
    public AccessResponseDto refresh(RefreshTokenRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        final String refreshToken = getFirstCookieValue(request, COOKIE_REFRESH_TOKEN)
                .orElseThrow(() -> new CookieNotFoundException("Cookie '" + COOKIE_REFRESH_TOKEN
                        + "' isn't provided in the request"));
        final String accessToken = dto.accessToken();
        final Claims accessTokenClaims;
        try {
            accessTokenClaims = accessJwtService.validateToken(
                    accessToken,
                    accessJwtService.getValidationParametersWithoutLifetime()
            ).claims();
        } catch (JwtException ex) {
            throw new InvalidTokenException("Passed access token is not valid", ex);
        }
        final String accessSubjectClaim = accessTokenClaims.getSubject();
        final UUID accessUserId;
        try {
            accessUserId = UUID.fromString(accessSubjectClaim);
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("Access token payload is not valid");
        }
        final Claims refreshClaims;
        try {
            refreshClaims = refreshJwtService.validateToken(
                    refreshToken,
                    refreshJwtService.getValidationParametersWithoutLifetime()
            ).claims();
        } catch (JwtException ex) {
            throw new InvalidTokenException("Refresh token payload is not valid", ex);
        }
        final String refreshSubjectClaim = refreshClaims.getSubject();
        final UUID refreshUserId;
        final UUID sessionId;
        try {
            refreshUserId = UUID.fromString(refreshSubjectClaim);
            sessionId = UUID.fromString((String) refreshClaims.get(refreshJwtService.REFRESH_SID));
        } catch (IllegalArgumentException | ClassCastException ex) {
            throw new InvalidTokenException("Refresh token payload is not valid");
        }
        if (!Objects.equals(accessUserId, refreshUserId))
            throw new InvalidTokenException("The refresh token can't be applied to the provided access token" +
                    " because tokens' payload is inconsistent");
        final User user = userRepository.findById(refreshUserId)
                .orElseThrow(() -> new InvalidTokenException("Tokens' subject not found"));
        final RefreshTokenKey refreshTokenKey = new RefreshTokenKey(user, sessionId);
        try {
            refreshJwtService.validateToken(
                    refreshToken,
                    refreshJwtService.getValidationParameters()
            );
        } catch (ExpiredJwtException ex) {
            refreshTokenRepository.deleteById(refreshTokenKey);
            throw new InvalidTokenException("The refresh token is expired. Try to login");
        }
        final RefreshToken refreshTokenEntity = refreshTokenRepository.findById(refreshTokenKey)
                .orElseThrow(() -> new InvalidTokenException("The refresh token is not valid"));
        final String newAccessToken = accessJwtService.createToken(accessJwtService.getClaims(user));
        final Claims newRefreshClaims = refreshJwtService.getClaims(user, sessionId);
        final String newRefreshToken = refreshJwtService.createToken(newRefreshClaims);
        final Instant refreshTokenExpirationTime = newRefreshClaims.getExpiration().toInstant();
        refreshTokenEntity.setExpirationTime(refreshTokenExpirationTime);
        refreshTokenRepository.saveAndFlush(refreshTokenEntity);
        final Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN, newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_REFRESH_PATH);
        cookie.setAttribute(COOKIE_ATTR_EXPIRES, expiresCookieFormatter.format(refreshTokenExpirationTime));
        response.addCookie(cookie);
        log.info("User { id = " + user.getId() + ", cardId = " + user.getCardId() + " } refreshed");
        return new AccessResponseDto(newAccessToken, user.getEmailConfirmed(), user.getPasswordNeedReset());
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        deleteRefreshTokenCookieIfPresent(request, response);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private void deleteRefreshTokenCookieIfPresent(HttpServletRequest request,
                                                   HttpServletResponse response) {
        getFirstCookieValue(request, COOKIE_REFRESH_TOKEN)
                .ifPresent(refreshToken -> {
                    Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN, "");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    final Claims refreshClaims;
                    try {
                        refreshClaims = refreshJwtService.validateToken(
                                refreshToken,
                                refreshJwtService.getValidationParametersWithoutLifetime()
                        ).claims();
                    } catch (JwtException ex) {
                        return;
                    }
                    final String refreshSubjectClaim = refreshClaims.getSubject();
                    final UUID refreshUserId;
                    final UUID sessionId;
                    try {
                        refreshUserId = UUID.fromString(refreshSubjectClaim);
                        sessionId = UUID.fromString((String) refreshClaims.get(refreshJwtService.REFRESH_SID));
                    } catch (IllegalArgumentException ex) {
                        return;
                    }
                    final User refUser = userRepository.getReferenceById(refreshUserId);
                    final RefreshTokenKey refreshTokenKey = new RefreshTokenKey(refUser, sessionId);
                    refreshTokenRepository.deleteById(refreshTokenKey);
                });
    }

    private static Optional<String> getFirstCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return Optional.empty();
        return Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), cookieName))
                .findFirst()
                .map(Cookie::getValue);
    }
}
