package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class EmailConfirmationProperties {
    private String confirmUrl;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration lifetime;
}
