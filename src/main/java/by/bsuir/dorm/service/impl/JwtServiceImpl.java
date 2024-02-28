package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final String stringKey = "test";
    private static final long accessTokenExpiration = 2 * 60 * 1000;

    @Override
    public String extractSubject(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(jwt);
        return claimsResolver.apply(claims);
    }
    @Override
    public Claims extractClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    @Override
    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, null);
    }

    @Override
    public String generateToken(UserDetails userDetails, Map<String, ?> extraClaims){
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String jwt, UserDetails user){
        final String subject = extractSubject(jwt);
        return Objects.equals(subject, user.getUsername()) && !isTokenExpired(jwt);
    }

    @Override
    public boolean isTokenExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    private SecretKey getSecretKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(stringKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}