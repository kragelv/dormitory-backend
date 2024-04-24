package by.bsuir.dorm.exception;

public class ViolationNotFoundException extends RuntimeException {
    public ViolationNotFoundException() {
        super();
    }

    public ViolationNotFoundException(String message) {
        super(message);
    }

    public ViolationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViolationNotFoundException(Throwable cause) {
        super(cause);
    }
}
