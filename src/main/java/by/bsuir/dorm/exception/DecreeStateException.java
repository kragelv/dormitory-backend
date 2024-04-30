package by.bsuir.dorm.exception;

public class DecreeStateException extends RuntimeException {
    public DecreeStateException() {
        super();
    }

    public DecreeStateException(String message) {
        super(message);
    }

    public DecreeStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecreeStateException(Throwable cause) {
        super(cause);
    }
}
