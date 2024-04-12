package by.bsuir.dorm.exception;

public class EmailNotAvailableException extends RuntimeException {
    public EmailNotAvailableException() {
        super();
    }

    public EmailNotAvailableException(String message) {
        super(message);
    }

    public EmailNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotAvailableException(Throwable cause) {
        super(cause);
    }
}
