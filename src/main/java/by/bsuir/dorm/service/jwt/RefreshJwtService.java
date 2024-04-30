package by.bsuir.dorm.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface RefreshJwtService extends JwtService {

    Claims getClaims(UserDetails userDetails, UUID pairId);

    JwtTokenValidationParameters getValidationParameters();

    JwtTokenValidationParameters getValidationParametersWithoutLifetime();
}
