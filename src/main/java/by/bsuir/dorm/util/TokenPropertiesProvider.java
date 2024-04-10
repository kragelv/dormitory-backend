package by.bsuir.dorm.util;

import by.bsuir.dorm.config.properties.AppProperties;
import by.bsuir.dorm.config.properties.AudiencesProperties;
import by.bsuir.dorm.config.properties.SecurityProperties;
import by.bsuir.dorm.service.Sha256;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
@Slf4j
@Getter()
public class TokenPropertiesProvider {
    private final Sha256 sha256;

    public record AccessTokenProperties(
            SecretKey secretKey,
            Duration duration
    ) {
    }

    public record RefreshTokenProperties(
            SecretKey secretKey,
            Duration duration
    ) {
    }

    private final String issuer;
    private final Set<String> audiences;
    private final AccessTokenProperties accessToken;
    private final RefreshTokenProperties refreshToken;

    @Autowired
    public TokenPropertiesProvider(Sha256 sha256,
                                   AppProperties appProperties,
                                   @Value("${spring.application.name}") String appName) {
        this.sha256 = sha256;
        final SecurityProperties securityProperties = appProperties.getSecurity();
        final Long accessTokenLifetime = securityProperties.getAccessToken().getLifetime();
        final String accessTokenSecret = securityProperties.getAccessToken().getB64Secret();
        final Long refreshTokenLifetime = securityProperties.getRefreshToken().getLifetime();
        final String refreshTokenSecret = securityProperties.getRefreshToken().getB64Secret();
        if (appName == null || appName.isEmpty())
            throw new IllegalArgumentException("Spring application name isn't set. Property uses as tokens issuer");
        if (accessTokenSecret == null || accessTokenSecret.isEmpty())
            throw new IllegalArgumentException("App property 'access-token.secret' isn't set");
        if (accessTokenLifetime == null)
            throw new IllegalArgumentException("App property 'access-token.lifetime' isn't set");
        if (refreshTokenSecret == null || refreshTokenSecret.isEmpty())
            throw new IllegalArgumentException("App property 'refresh-token.secret' isn't set");
        if (refreshTokenLifetime == null)
            throw new IllegalArgumentException("App property 'refresh-token.lifetime' isn't set");
        this.issuer = appName;
        log.info("Issuer set from properties: " + this.issuer);

        accessToken = new AccessTokenProperties(
                Keys.hmacShaKeyFor(sha256.hash(Decoders.BASE64.decode(accessTokenSecret))),
                Duration.of(accessTokenLifetime, ChronoUnit.SECONDS)
        );
        log.info("Access token secret set from properties");
        log.info("Access token lifetime set from properties: " + accessToken.duration());
        refreshToken = new RefreshTokenProperties(
                Keys.hmacShaKeyFor(sha256.hash(Decoders.BASE64.decode(refreshTokenSecret))),
                Duration.ofSeconds(refreshTokenLifetime)
        );
        log.info("Refresh token secret set from properties");
        log.info("Refresh token lifetime set from properties: " + refreshToken.duration());
        this.audiences = appProperties.getSecurity()
                .getAudiences()
                .stream()
                .map(AudiencesProperties::getName)
                .collect(Collectors.toSet());
    }
}
