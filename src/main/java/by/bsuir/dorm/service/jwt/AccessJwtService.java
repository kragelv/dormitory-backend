package by.bsuir.dorm.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccessJwtService extends JwtService {
    String ACCESS_AUTHORITIES = "ath";

    Claims getClaims(UserDetails user);

    JwtTokenValidationParameters getValidationParametersJwtAuthenticationFilter();

    JwtTokenValidationParameters getValidationParametersWithoutLifetime();
}
