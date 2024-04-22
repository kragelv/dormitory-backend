package by.bsuir.dorm.exception;

public class AuthBearerTokenException extends RuntimeException {
    public AuthBearerTokenException() {
        super();
    }

    public AuthBearerTokenException(String message) {
        super(message);
    }

    public AuthBearerTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthBearerTokenException(Throwable cause) {
        super(cause);
    }
}
