package by.bsuir.dorm.exception;

public class EmailNotPresentException extends RuntimeException {
    public EmailNotPresentException() {
        super();
    }

    public EmailNotPresentException(String message) {
        super(message);
    }

    public EmailNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotPresentException(Throwable cause) {
        super(cause);
    }
}
