package by.bsuir.dorm.service;

import by.bsuir.dorm.util.JwtTokenDescriptor;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    Claims extractClaims(String jwt);

    JwtTokenDescriptor generateToken(UserDetails userDetails);

    JwtTokenDescriptor generateToken(UserDetails userDetails, Map<String, ?> extraClaims);

    boolean isTokenValid(String jwt, UserDetails user);

    boolean isTokenActive(String jwt);
}
