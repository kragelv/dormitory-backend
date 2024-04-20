package by.bsuir.dorm.service.jwt;

import by.bsuir.dorm.model.entity.User;
import io.jsonwebtoken.Claims;

public interface AccessJwtService extends JwtService {
    String ACCESS_AUTHORITIES = "ath";

    String ACCESS_EMAIL_CONFIRMED = "emc";

    String ACCESS_PASSWORD_NEED_RESET = "pnr";

    Claims getClaims(User user);

    JwtTokenValidationParameters getValidationParametersJwtAuthenticationFilter();

    JwtTokenValidationParameters getValidationParametersWithoutLifetime();
}
