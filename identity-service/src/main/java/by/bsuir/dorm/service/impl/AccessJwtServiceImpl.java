package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.service.AccessJwtService;
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

@Service
@RequiredArgsConstructor
public class AccessJwtServiceImpl implements AccessJwtService {
    private final TokenPropertiesProvider tokenPropertiesProvider;

    @Override
    public Claims extractClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith(tokenPropertiesProvider.getAccessTokenSecretKey())
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
        final Instant now = Instant.now();
        final Claims claims = Jwts
                .claims()
                .add(extraClaims)
                .subject(userDetails.getUsername())
                .issuer(tokenPropertiesProvider.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(tokenPropertiesProvider.getAccessTokenDuration())))
                .build();
        final String token = Jwts
                .builder()
                .claims(claims)
                .signWith(tokenPropertiesProvider.getAccessTokenSecretKey(), Jwts.SIG.HS256)
                .compact();
        return new JwtTokenDescriptor(claims, token);
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
    public boolean isRefreshedTokenValid(String jwt) {
        final Claims claims = extractClaims(jwt);
        final String issuer = claims.getIssuer();
        return Objects.equals(issuer, tokenPropertiesProvider.getIssuer());
    }
}