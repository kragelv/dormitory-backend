package by.bsuir.dorm.exception.handler;

import by.bsuir.dorm.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(AuthBearerTokenException.class)
    public ErrorResponseEntity handleAuthBearerTokenException(final AuthBearerTokenException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LoginException.class)
    public ErrorResponseEntity handleLoginException(final LoginException ex) {
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
                .status(HttpStatus.UNAUTHORIZED)
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

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ErrorResponseEntity handleRoomAlreadyExistsException(final RoomAlreadyExistsException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoomStateException.class)
    public ErrorResponseEntity handleRoomStateException(final RoomStateException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ErrorResponseEntity handleContractNotFoundException(final ContractNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotPresentException.class)
    public ErrorResponseEntity handleEmailNotPresentException(final EmailNotPresentException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ErrorResponseEntity handleStudentAlreadyExistsException(final StudentAlreadyExistsException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StudentAlreadyHasContractException.class)
    public ErrorResponseEntity handleStudentAlreadyHasContractException(
            final StudentAlreadyHasContractException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LeisureNotFoundException.class)
    public ErrorResponseEntity handleLeisureNotFoundException(final LeisureNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LeisureStateException.class)
    public ErrorResponseEntity handleLeisureStateException(final LeisureStateException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GroupStateException.class)
    public ErrorResponseEntity handleGroupStateException(final GroupStateException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RegulationNotFoundException.class)
    public ErrorResponseEntity handleRegulationNotFoundException(final RegulationNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegulationStateException.class)
    public ErrorResponseEntity handleRegulationStateException(final RegulationStateException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RegulationAlreadyExistsException.class)
    public ErrorResponseEntity handleRegulationAlreadyExistsException(
            final RegulationAlreadyExistsException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReportingNoteNotFoundException.class)
    public ErrorResponseEntity handleReportingNoteNotFoundException(final ReportingNoteNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportingNoteStateException.class)
    public ErrorResponseEntity handleReportingNoteStateException(final ReportingNoteStateException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ViolationNotFoundException.class)
    public ErrorResponseEntity handleViolationNotFoundException(final ViolationNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExplanatoryNotFoundException.class)
    public ErrorResponseEntity handleExplanatoryNotFoundException(final ExplanatoryNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExplanatoryNoteAlreadyExistsException.class)
    public ErrorResponseEntity handleExplanatoryNoteAlreadyExistsException(
            final ExplanatoryNoteAlreadyExistsException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DecreeNotFoundException.class)
    public ErrorResponseEntity handleDecreeNotFoundException(final DecreeNotFoundException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DecreeStateException.class)
    public ErrorResponseEntity handleDecreeStateException(final DecreeStateException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDecreeDataException.class)
    public ErrorResponseEntity handleInvalidDecreeDataException(
            final InvalidDecreeDataException ex) {
        return ErrorResponseEntity.create(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity handleRuntimeException(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorResponseEntity
                .builder(ex)
                .message("[" + ProcessHandle.current().pid() + "] " + ex.getClass().getCanonicalName() + ": " + ex.getMessage())
                .build();
    }
}
