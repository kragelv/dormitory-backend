package by.bsuir.dorm.exception;

public class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException() {
        super();
    }

    public InvalidUserTypeException(String message) {
        super(message);
    }

    public InvalidUserTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserTypeException(Throwable cause) {
        super(cause);
    }
}
