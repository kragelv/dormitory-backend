package by.bsuir.dorm.util;

import by.bsuir.dorm.config.properties.AppProperties;
import by.bsuir.dorm.config.properties.RefreshTokenProperties;
import by.bsuir.dorm.config.properties.SecurityProperties;
import by.bsuir.dorm.service.Sha256;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@Scope("singleton")
@Slf4j
@Getter
public class TokenPropertiesProvider {
    private final Sha256 sha256;

    private final String issuer;
    private final SecretKey accessTokenSecretKey;
    private final Duration accessTokenDuration;
    private final SecretKey refreshTokenSecretKey;
    private final Duration refreshTokenDuration;

    @Autowired
    public TokenPropertiesProvider(Sha256 sha256,
                                   AppProperties appProperties) {
        this.sha256 = sha256;
        final SecurityProperties securityProperties = appProperties.getSecurity();
        final String issuer  = securityProperties.getIssuer();
        final Long accessTokenLifetime = securityProperties.getAccessToken().getLifetime();
        final String accessTokenSecret = securityProperties.getAccessToken().getB64Secret();
        final Long refreshTokenLifetime = securityProperties.getRefreshToken().getLifetime();
        final String refreshTokenSecret = securityProperties.getRefreshToken().getB64Secret();
        log.warn(PropertyPath.from("lifetime", RefreshTokenProperties.class).toDotPath());
        if (issuer == null || issuer.isEmpty())
            throw new IllegalArgumentException("App property 'issuer' isn't set");
        if (accessTokenSecret == null || accessTokenSecret.isEmpty())
            throw new IllegalArgumentException("App property 'access-token.secret' isn't set");
        if (accessTokenLifetime == null)
            throw new IllegalArgumentException("App property 'access-token.lifetime' isn't set");
        if (refreshTokenSecret == null || refreshTokenSecret.isEmpty())
            throw new IllegalArgumentException("App property 'refresh-token.secret' isn't set");
        if (refreshTokenLifetime == null)
            throw new IllegalArgumentException("App property 'refresh-token.lifetime' isn't set");
        this.issuer = issuer;
        log.info("Issuer set from properties: " + issuer);
        this.accessTokenDuration = Duration.of(accessTokenLifetime, ChronoUnit.SECONDS);
        log.info("Access token lifetime set from properties: " + accessTokenDuration);
        accessTokenSecretKey = Keys.hmacShaKeyFor(sha256.hash(Decoders.BASE64.decode(accessTokenSecret)));
        log.info("Access token secret set from properties");
        this.refreshTokenDuration = Duration.of(refreshTokenLifetime, ChronoUnit.SECONDS);
        log.info("Refresh token lifetime set from properties: " + refreshTokenDuration);
        refreshTokenSecretKey = Keys.hmacShaKeyFor(sha256.hash(Decoders.BASE64.decode(refreshTokenSecret)));
        log.info("Refresh token secret set from properties");
    }
}
