package by.bsuir.dorm.exception;

public class DecreeNotFoundException extends RuntimeException {
    public DecreeNotFoundException() {
        super();
    }

    public DecreeNotFoundException(String message) {
        super(message);
    }

    public DecreeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecreeNotFoundException(Throwable cause) {
        super(cause);
    }
}
