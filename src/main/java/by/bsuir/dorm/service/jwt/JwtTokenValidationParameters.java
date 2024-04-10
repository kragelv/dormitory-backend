package by.bsuir.dorm.service.jwt;

import lombok.Builder;

import java.util.Collection;

@Builder
public record JwtTokenValidationParameters(
        boolean verifySigningKey,
        boolean validateLifetime,
        long clockSkewSeconds,
        String validIssuer,
        Collection<? extends String> validAudience
) {
}
