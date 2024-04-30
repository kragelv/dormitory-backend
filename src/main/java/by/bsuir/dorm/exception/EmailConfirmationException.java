package by.bsuir.dorm.exception;

public class EmailConfirmationException extends RuntimeException {
    public EmailConfirmationException() {
        super();
    }

    public EmailConfirmationException(String message) {
        super(message);
    }

    public EmailConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailConfirmationException(Throwable cause) {
        super(cause);
    }
}
