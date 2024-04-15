package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class PasswordResetProperties {
    private String resetUrl;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration lifetime;
}
