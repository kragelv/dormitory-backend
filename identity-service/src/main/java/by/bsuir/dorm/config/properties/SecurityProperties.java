package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@Getter
@Setter
public class SecurityProperties {
    private String issuer;

    @NestedConfigurationProperty
    private AccessTokenProperties accessToken;

    @NestedConfigurationProperty
    private RefreshTokenProperties refreshToken;

    @NestedConfigurationProperty
    private List<AudiencesProperties> audiences;
}
