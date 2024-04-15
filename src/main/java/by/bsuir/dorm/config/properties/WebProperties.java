package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
public class WebProperties {
    @NestedConfigurationProperty
    private CorsProperties cors;
}
