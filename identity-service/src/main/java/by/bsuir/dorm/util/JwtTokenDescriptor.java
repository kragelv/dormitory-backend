package by.bsuir.dorm.util;

import io.jsonwebtoken.Claims;

public record JwtTokenDescriptor(
        Claims claims,
        String token
) {
}
