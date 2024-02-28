package by.bsuir.dorm.service;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractSubject(String jwt);

    Claims extractClaims(String jwt);

    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, Map<String, ?> extraClaims);

    boolean isTokenValid(String jwt, UserDetails user);

    boolean isTokenExpired(String jwt);
}
