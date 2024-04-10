package by.bsuir.dorm.config.properties;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class PasswordResetProperties {
    private String resetUrl;
    private Duration lifetime;

    public String getResetUrl() {
        return resetUrl;
    }

    public void setResetUrl(String resetUrl) {
        this.resetUrl = resetUrl;
        log.info("Password reset url set: " + this.resetUrl);
    }

    public Duration getLifetime() {
        return lifetime;
    }

    public void setLifetime(Long lifetime) {
        this.lifetime = Duration.ofSeconds(lifetime);
        log.info("Password token lifetime set: " + this.lifetime);
    }
}
