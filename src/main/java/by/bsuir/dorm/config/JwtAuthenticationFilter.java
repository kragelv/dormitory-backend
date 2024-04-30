package by.bsuir.dorm.config;

import by.bsuir.dorm.exception.AuthBearerTokenException;
import by.bsuir.dorm.exception.InvalidTokenException;
import by.bsuir.dorm.service.jwt.AccessJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final AccessJwtService accessJwtService;
    private final UserDetailsService userDetailsService;

    private String castToStringOrElseThrow(Object o) {
        if (!(o instanceof String))
            throw new InvalidTokenException("Bearer token payload is invalid");
        return (String) o;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(BEARER_PREFIX.length());
        final Claims claims;
        try {
            claims = accessJwtService.validateToken(
                    jwt,
                    accessJwtService.getValidationParametersJwtAuthenticationFilter()
            ).claims();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (JwtException ex) {
            throw new AuthBearerTokenException("Bearer token is invalid", ex);
        }
        final String id = claims.getSubject();
        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(id);
            if (Objects.equals(userDetails.getUsername(), id)) {
                Collection<? extends GrantedAuthority> tokenAuthorities;
                if (claims.get(accessJwtService.ACCESS_AUTHORITIES) instanceof Collection<?> collection) {
                    tokenAuthorities = collection
                            .stream()
                            .map(this::castToStringOrElseThrow)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet());
                } else {
                    tokenAuthorities = Set.of();
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        tokenAuthorities
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
