package by.bsuir.dorm.exception.handler;

import by.bsuir.dorm.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseEntity handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(e -> "Field '" + e.getField() + "' with value '"
                        + e.getRejectedValue() + "' rejected with cause: '" + e.getDefaultMessage() + "'"
                )
                .toList();
        return ErrorResponseEntity
                .builder()
                .message("Validation error: " + String.join("; ", errors))
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(PropertyAccessException.class)
    public ErrorResponseEntity handlePropertyAccessException(
            final PropertyAccessException ex) {
        return ErrorResponseEntity
                .builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ErrorResponseEntity handleErrorResponseException(final ErrorResponseException ex) {
        return ErrorResponseEntity
                .builder()
                .message(ex.getBody().getDetail())
                .status(HttpStatus.valueOf(ex.getStatusCode().value()))
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponseEntity handleUsernameNotFoundException(final UsernameNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LoginException.class)
    public ErrorResponseEntity handleAuthException(final LoginException ex) {
        final Throwable cause = ex.getCause();
        final String message;
        if (cause != null) {
            message = ex.getMessage() + ": " + cause.getMessage();
        } else {
            message = ex.getMessage();
        }
        return ErrorResponseEntity
                .builder()
                .message(message)
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponseEntity handleExpiredJwtException(final ExpiredJwtException ex) {
        return ErrorResponseEntity
                .builder()
                .message("Bearer token expired: " + ex.getClaims().getExpiration().toInstant())
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(JwtException.class)
    public ErrorResponseEntity handleJwtException(final JwtException ex) {
        return ErrorResponseEntity
                .builder()
                .message("Invalid token: " + ex.getMessage())
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

    @ExceptionHandler(UserTypeNotFoundException.class)
    public ErrorResponseEntity handleInvalidUserTypeException(final UserTypeNotFoundException ex) {
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

    @ExceptionHandler(RoleNotCompatibleException.class)
    public ErrorResponseEntity handleRoleNotCompatibleException(final RoleNotCompatibleException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailNotAvailableException.class)
    public ErrorResponseEntity handleEmailNotAvailableException(final EmailNotAvailableException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailConfirmationException.class)
    public ErrorResponseEntity handleEmailConfirmationException(final EmailConfirmationException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PasswordResetException.class)
    public ErrorResponseEntity handlePasswordResetException(final PasswordResetException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ErrorResponseEntity handleRoomNotFoundException(final RoomNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ErrorResponseEntity handleContractNotFoundException(final ContractNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity handleRuntimeException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorResponseEntity
                .builder(ex)
                .message(ex.getClass().getCanonicalName() + ": " + ex.getMessage())
                .build();
    }
}
