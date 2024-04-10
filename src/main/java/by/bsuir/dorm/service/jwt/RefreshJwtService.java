package by.bsuir.dorm.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface RefreshJwtService extends JwtService {
    String REFRESH_SID = "sid";

    Claims getClaims(UserDetails userDetails, UUID sessionId);

    JwtTokenValidationParameters getValidationParameters();

    JwtTokenValidationParameters getValidationParametersWithoutLifetime();
}
