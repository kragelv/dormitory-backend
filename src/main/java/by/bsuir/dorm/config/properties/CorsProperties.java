package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorsProperties {
    private AllowedOrigins allowedOrigins;
    private AllowedMethods allowedMethods;
    private AllowedHeaders allowedHeaders;
    private ExposedHeaders exposedHeaders = null;
    private Boolean allowCredentials = null;
    private Long maxAge = null;
}
