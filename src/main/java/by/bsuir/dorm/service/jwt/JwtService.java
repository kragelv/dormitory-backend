package by.bsuir.dorm.service.jwt;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;

public interface JwtService {
    SecretKey getSecretKey();

    default String createToken(Claims claims) {
        return Jwts
                .builder()
                .claims(claims)
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    default JwtTokenDescriptor validateToken(String jwt, JwtTokenValidationParameters parameters) {
        JwtParserBuilder parserBuilder = Jwts.parser();
        if (parameters.validateLifetime()) {
            parserBuilder.clockSkewSeconds(parameters.clockSkewSeconds());
        }
        if (parameters.validIssuer() != null) {
            parserBuilder.requireIssuer(parameters.validIssuer());
        }
        if (parameters.validAudience() != null) {
            parameters.validAudience().forEach(parserBuilder::requireAudience);
        }
        if (parameters.verifySigningKey()) {
            parserBuilder.verifyWith(getSecretKey());
        } else {
            parserBuilder.unsecured();
        }
        JwtParser parser = parserBuilder.build();
        try {
            final Jws<Claims> tokenContent = parser.parseSignedClaims(jwt);
            return new JwtTokenDescriptor(tokenContent.getHeader(), tokenContent.getPayload());
        } catch (ExpiredJwtException ex) {
            if (parameters.validateLifetime())
                throw ex;
            return new JwtTokenDescriptor(ex.getHeader(), ex.getClaims());
        }
    }
}
