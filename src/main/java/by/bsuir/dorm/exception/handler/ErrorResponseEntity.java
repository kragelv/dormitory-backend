package by.bsuir.dorm.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ErrorResponseEntity extends ResponseEntity<ErrorResponseDto> {
    public ErrorResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public ErrorResponseEntity(ErrorResponseDto body, HttpStatusCode status) {
        super(body, status);
    }

    public static ErrorResponseEntity create(Throwable t, HttpStatus status) {
        return builder(t)
                .status(status)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Throwable t) {
        return new Builder(t);
    }

    public static class Builder {
        private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
        private Instant timestamp;
        private HttpStatus httpStatus;
        private String error;
        private String message;

        Builder() {
            this.timestamp = Instant.now();
            this.httpStatus = DEFAULT_STATUS;
        }

        Builder(Throwable t) {
            this();
            this.message = t.getMessage();
        }

        Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        Builder status(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        Builder error(String error) {
            this.error = error;
            return this;
        }

        Builder message(String message) {
            this.message = message;
            return this;
        }

        ErrorResponseEntity build() {
            return new ErrorResponseEntity(
                    new ErrorResponseDto(
                            timestamp,
                            httpStatus.value(),
                            error == null ? httpStatus.name() : error,
                            message),
                    httpStatus
            );
        }
    }
}
