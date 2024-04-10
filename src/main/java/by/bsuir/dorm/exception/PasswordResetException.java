package by.bsuir.dorm.exception;

public class PasswordResetException extends RuntimeException {
    public PasswordResetException() {
        super();
    }

    public PasswordResetException(String message) {
        super(message);
    }

    public PasswordResetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetException(Throwable cause) {
        super(cause);
    }
}
