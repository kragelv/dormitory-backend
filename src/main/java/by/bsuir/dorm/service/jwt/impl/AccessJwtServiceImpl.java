package by.bsuir.dorm.service.jwt.impl;

import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.service.jwt.AccessJwtService;
import by.bsuir.dorm.service.jwt.JwtTokenValidationParameters;
import by.bsuir.dorm.util.TokenPropertiesProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class AccessJwtServiceImpl implements AccessJwtService {
    private final TokenPropertiesProvider tokenPropertiesProvider;

    private final JwtTokenValidationParameters validationParametersJwtAuthenticationFilter;
    private final JwtTokenValidationParameters validationParametersWithoutLifetime;

    @Autowired
    public AccessJwtServiceImpl(TokenPropertiesProvider tokenPropertiesProvider) {
        this.tokenPropertiesProvider = tokenPropertiesProvider;
        validationParametersJwtAuthenticationFilter = JwtTokenValidationParameters
                .builder()
                .validateLifetime(true)
                .verifySigningKey(true)
                .validIssuer(tokenPropertiesProvider.getIssuer())
                .validAudience(tokenPropertiesProvider.getAudiences())
                .build();
        validationParametersWithoutLifetime = JwtTokenValidationParameters
                .builder()
                .verifySigningKey(true)
                .validIssuer(tokenPropertiesProvider.getIssuer())
                .validAudience(tokenPropertiesProvider.getAudiences())
                .build();
    }

    @Override
    public SecretKey getSecretKey() {
        return tokenPropertiesProvider.getAccessToken().secretKey();
    }

    @Override
    public Claims getClaims(User user) {
        final Instant now = Instant.now();
        final Instant expirationTime = now.plus(tokenPropertiesProvider.getAccessToken().duration());
        return Jwts
                .claims()
                .add(
                        ACCESS_AUTHORITIES,
                        user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet())
                )
                .add(ACCESS_EMAIL_CONFIRMED, user.getEmailConfirmed())
                .add(ACCESS_PASSWORD_NEED_RESET, user.getPasswordNeedReset())
                .subject(user.getUsername())
                .audience()
                .add(tokenPropertiesProvider.getAudiences())
                .and()
                .issuer(tokenPropertiesProvider.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .build();
    }

    @Override
    public JwtTokenValidationParameters getValidationParametersJwtAuthenticationFilter() {
        return validationParametersJwtAuthenticationFilter;
    }

    @Override
    public JwtTokenValidationParameters getValidationParametersWithoutLifetime() {
        return validationParametersWithoutLifetime;
    }
}
