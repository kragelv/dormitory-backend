package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenProperties {
    private Long lifetime;
    private String b64Secret;
}