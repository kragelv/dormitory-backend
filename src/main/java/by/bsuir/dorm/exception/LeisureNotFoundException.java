package by.bsuir.dorm.exception;

public class LeisureNotFoundException extends RuntimeException {
    public LeisureNotFoundException() {
        super();
    }

    public LeisureNotFoundException(String message) {
        super(message);
    }

    public LeisureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LeisureNotFoundException(Throwable cause) {
        super(cause);
    }
}
