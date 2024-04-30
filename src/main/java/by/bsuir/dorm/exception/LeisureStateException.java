package by.bsuir.dorm.exception;

public class LeisureStateException extends RuntimeException {
    public LeisureStateException() {
        super();
    }

    public LeisureStateException(String message) {
        super(message);
    }

    public LeisureStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LeisureStateException(Throwable cause) {
        super(cause);
    }
}
