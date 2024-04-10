package by.bsuir.dorm.config.properties;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class EmailConfirmationProperties {
    private String confirmUrl;
    private Duration lifetime;

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public void setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl;
        log.info("Email confirmation url set: " + this.confirmUrl);
    }

    public Duration getLifetime() {
        return lifetime;
    }

    public void setLifetime(Long lifetime) {
        this.lifetime = Duration.ofSeconds(lifetime);
        log.info("Email token lifetime set: " + this.lifetime);
    }
}
