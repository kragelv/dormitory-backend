package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.entity.User;
import by.bsuir.dorm.service.RefreshJwtService;
import by.bsuir.dorm.util.JwtTokenDescriptor;
import by.bsuir.dorm.util.TokenPropertiesProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshJwtServiceImpl implements RefreshJwtService {
    private final TokenPropertiesProvider tokenPropertiesProvider;
    private final UserRepository userRepository;

    @Override
    public Claims extractClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith(tokenPropertiesProvider.getRefreshTokenSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    @Override
    public JwtTokenDescriptor generateToken(UserDetails userDetails){
        return generateToken(userDetails, null);
    }

    @Override
    public JwtTokenDescriptor generateToken(UserDetails userDetails, Map<String, ?> extraClaims){
        final UUID userId = UUID.fromString(userDetails.getUsername());
        final User refUser = userRepository.getReferenceById(userId);
        return generateToken(refUser, extraClaims);
    }

    @Override
    public boolean isTokenValid(String jwt, UserDetails user){
        final Claims claims = extractClaims(jwt);
        final String subject = claims.getSubject();
        final String issuer = claims.getIssuer();
        return Objects.equals(issuer, tokenPropertiesProvider.getIssuer())
                && Objects.equals(subject, user.getUsername())
                && isTokenActive(jwt);
    }

    @Override
    public boolean isTokenActive(String jwt) {
        return extractClaims(jwt).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    @Override
    public JwtTokenDescriptor generateToken(User user) {
        return generateToken(user, null);
    }

    @Override
    public JwtTokenDescriptor generateToken(User user, Map<String, ?> extraClaims) {
        final Instant now = Instant.now();
        final Instant expirationTime = now.plus(tokenPropertiesProvider.getRefreshTokenDuration());
        final Claims claims = Jwts
                .claims()
                .add(extraClaims)
                .subject(user.getUsername())
                .issuer(tokenPropertiesProvider.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .build();
        final String token = Jwts
                .builder()
                .claims(claims)
                .signWith(tokenPropertiesProvider.getRefreshTokenSecretKey(), Jwts.SIG.HS256)
                .compact();
        return new JwtTokenDescriptor(claims, token);
    }
}
