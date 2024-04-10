package by.bsuir.dorm.service.jwt.impl;

import by.bsuir.dorm.service.jwt.JwtTokenValidationParameters;
import by.bsuir.dorm.service.jwt.RefreshJwtService;
import by.bsuir.dorm.util.TokenPropertiesProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshJwtServiceImpl implements RefreshJwtService {
    private final TokenPropertiesProvider tokenPropertiesProvider;

    private final JwtTokenValidationParameters validationParameters;
    private final JwtTokenValidationParameters validationParametersWithoutLifetime;

    @Autowired
    public RefreshJwtServiceImpl(TokenPropertiesProvider tokenPropertiesProvider) {
        this.tokenPropertiesProvider = tokenPropertiesProvider;
        validationParameters = JwtTokenValidationParameters
                .builder()
                .validateLifetime(true)
                .verifySigningKey(true)
                .validIssuer(tokenPropertiesProvider.getIssuer())
                .build();
        validationParametersWithoutLifetime = JwtTokenValidationParameters
                .builder()
                .verifySigningKey(true)
                .validIssuer(tokenPropertiesProvider.getIssuer())
                .build();
    }

    @Override
    public SecretKey getSecretKey() {
        return tokenPropertiesProvider.getRefreshToken().secretKey();
    }

    @Override
    public Claims getClaims(UserDetails userDetails, UUID sessionId) {
        final Instant now = Instant.now();
        final Instant expirationTime = now.plus(tokenPropertiesProvider.getRefreshToken().duration());
        return Jwts
                .claims()
                .subject(userDetails.getUsername())
                .add(REFRESH_SID, sessionId)
                .issuer(tokenPropertiesProvider.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .build();
    }

    @Override
    public JwtTokenValidationParameters getValidationParameters() {
        return validationParameters;
    }

    @Override
    public JwtTokenValidationParameters getValidationParametersWithoutLifetime() {
        return validationParametersWithoutLifetime;
    }
}
