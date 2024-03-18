package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.RefreshTokenRepository;
import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.request.LoginUserRequestDto;
import by.bsuir.dorm.dto.request.RefreshTokenRequestDto;
import by.bsuir.dorm.dto.response.AccessResponseDto;
import by.bsuir.dorm.entity.RefreshToken;
import by.bsuir.dorm.entity.RefreshTokenKey;
import by.bsuir.dorm.entity.User;
import by.bsuir.dorm.exception.CookieNotFoundException;
import by.bsuir.dorm.exception.InvalidTokenException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.service.AccessJwtService;
import by.bsuir.dorm.service.AuthService;
import by.bsuir.dorm.service.RefreshJwtService;
import by.bsuir.dorm.util.JwtTokenDescriptor;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String COOKIE_REFRESH_TOKEN = "refresh-token";
    private static final String COOKIE_REFRESH_PATH = "/api/v1/auth";
    private static final String COOKIE_ATTR_EXPIRES = "Expires";
    private static final DateTimeFormatter expiresCookieFormatter =
            DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);
    private static final String REFRESH_SID = "sid";
    private static final String ACCESS_ROLES = "roles";

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AccessJwtService accessJwtService;
    private final RefreshJwtService refreshJwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    private record RefreshTokenKeyClaims(UUID userId, UUID sessionId) { }

    @Override
    public AccessResponseDto login(LoginUserRequestDto dto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        deleteRefreshTokenCookieIfPresent(request, response);
        User user = userRepository.findByCardId(dto.cardId())
                .orElseThrow(() -> new UserNotFoundException("User { cardId = " + dto.cardId() + " } doesn't exist"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        dto.password()
                )
        ); //exception if credentials are not valid
        final JwtTokenDescriptor accessTokenDesc = accessJwtService.generateToken(user, getRoleClaims(user));
        final UUID sessionId = UUID.randomUUID();
        final JwtTokenDescriptor refreshTokenDesc = refreshJwtService.generateToken(user, getSessionClaim(sessionId));
        final Instant refreshTokenExpirationTime = refreshTokenDesc.claims().getExpiration().toInstant();
        final RefreshTokenKey refreshTokenKey = new RefreshTokenKey(user, sessionId);
        final RefreshToken refreshTokenEntity = new RefreshToken(refreshTokenKey, refreshTokenExpirationTime);
        refreshTokenRepository.saveAndFlush(refreshTokenEntity);
        final Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN, refreshTokenDesc.token());
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_REFRESH_PATH);
        cookie.setAttribute(COOKIE_ATTR_EXPIRES, expiresCookieFormatter.format(refreshTokenExpirationTime));
        response.addCookie(cookie);
        log.info("User { id = " + user.getId() + ", cardId = " + user.getCardId() + " } logged in");
        return new AccessResponseDto(accessTokenDesc.token());
    }

    @Override
    public AccessResponseDto refresh(RefreshTokenRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        final String refreshToken = getFirstCookieValue(request, COOKIE_REFRESH_TOKEN)
                .orElseThrow(() -> new CookieNotFoundException("Cookie '" + COOKIE_REFRESH_TOKEN
                        + "' isn't provided in the request"));
        final String accessToken = dto.accessToken();
        if (!accessJwtService.isRefreshedTokenValid(accessToken))
            throw new InvalidTokenException("Passed access token is not valid");
        final String accessSubjectClaim = accessJwtService.extractClaims(accessToken).getSubject();
        final UUID accessUserId;
        try {
            accessUserId = UUID.fromString(accessSubjectClaim);
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("Access token payload is not valid");
        }
        RefreshTokenKeyClaims refreshKeyClaims = extractRefreshTokenKeyClaims(refreshToken);
        if (!Objects.equals(accessUserId, refreshKeyClaims.userId()))
            throw new InvalidTokenException("The refresh token can't be applied to the provided access token" +
                    " because tokens' payload is inconsistent");
        final User user = userRepository.findById(refreshKeyClaims.userId())
                .orElseThrow(() -> new InvalidTokenException("Tokens' subject not found"));
        final RefreshTokenKey refreshTokenKey = new RefreshTokenKey(user, refreshKeyClaims.sessionId());
        if (!refreshJwtService.isTokenActive(refreshToken)) {
            refreshTokenRepository.deleteById(refreshTokenKey);
            throw new InvalidTokenException("The refresh token is expired. Try to login");
        }
        final RefreshToken refreshTokenEntity = refreshTokenRepository.findById(refreshTokenKey)
                .orElseThrow(() -> new InvalidTokenException("The refresh token is not valid"));
        final JwtTokenDescriptor accessTokenDesc = accessJwtService.generateToken(user, getRoleClaims(user));
        final JwtTokenDescriptor refreshTokenDesc = refreshJwtService.generateToken(user, getSessionClaim(refreshKeyClaims.sessionId()));
        final Instant refreshTokenExpirationTime = refreshTokenDesc.claims().getExpiration().toInstant();
        refreshTokenEntity.setExpirationTime(refreshTokenExpirationTime);
        refreshTokenRepository.saveAndFlush(refreshTokenEntity);
        final Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN, refreshTokenDesc.token());
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_REFRESH_PATH);
        cookie.setAttribute(COOKIE_ATTR_EXPIRES, expiresCookieFormatter.format(refreshTokenExpirationTime));
        response.addCookie(cookie);
        log.info("User { id = " + user.getId() + ", cardId = " + user.getCardId() + " } refreshed");
        return new AccessResponseDto(accessTokenDesc.token());
    }

    private RefreshTokenKeyClaims extractRefreshTokenKeyClaims(String refreshToken) {
        final Claims refreshClaims = refreshJwtService.extractClaims(refreshToken);
        final String refreshSubjectClaim = refreshClaims.getSubject();
        final UUID refreshUserId;
        final UUID sessionId;
        try {
            refreshUserId = UUID.fromString(refreshSubjectClaim);
            sessionId = UUID.fromString((String) refreshClaims.get(REFRESH_SID));
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("Refresh token payload is not valid");
        }
        return new RefreshTokenKeyClaims(refreshUserId, sessionId);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        deleteRefreshTokenCookieIfPresent(request, response);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
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
                    RefreshTokenKeyClaims refreshKeyClaims = extractRefreshTokenKeyClaims(refreshToken);
                    final User refUser = userRepository.getReferenceById(refreshKeyClaims.userId());
                    final RefreshTokenKey refreshTokenKey = new RefreshTokenKey(refUser, refreshKeyClaims.sessionId());
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

    private static Map<String, UUID> getSessionClaim(UUID sessionId) {
        return Map.of(REFRESH_SID, sessionId);
    }

    private static Map<String, ?> getRoleClaims(User user) {
        return Map.of(
                ACCESS_ROLES,
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
        );
    }

}
