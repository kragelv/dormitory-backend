package by.bsuir.dorm.exception.handler;

import by.bsuir.dorm.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponseEntity handleUsernameNotFoundException(final UsernameNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponseEntity handleExpiredJwtException(final ExpiredJwtException ex) {
        return ErrorResponseEntity
                .builder()
                .message("Bearer token expired")
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(CookieNotFoundException.class)
    public ErrorResponseEntity handleCookieNotFoundException(final CookieNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ErrorResponseEntity handleInvalidTokenException(final InvalidTokenException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUserTypeException.class)
    public ErrorResponseEntity handleInvalidUserTypeException(final InvalidUserTypeException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseEntity handleUserNotFoundException(final UserNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ErrorResponseEntity handleRoleNotFoundException(final RoleNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity handleRuntimeException(final RuntimeException ex) {
        return ErrorResponseEntity
                .builder(ex)
                .message(ex.getClass().getCanonicalName() + ": " + ex.getMessage())
                .build();
    }
}
