package by.bsuir.dorm.exception;

public class ExplanatoryNotFoundException extends RuntimeException {
    public ExplanatoryNotFoundException() {
        super();
    }

    public ExplanatoryNotFoundException(String message) {
        super(message);
    }

    public ExplanatoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExplanatoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
