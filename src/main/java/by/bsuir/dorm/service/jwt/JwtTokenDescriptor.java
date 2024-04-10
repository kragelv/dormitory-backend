package by.bsuir.dorm.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public record JwtTokenDescriptor(
        Header header,
        Claims claims
) {
}
