package by.bsuir.dorm.exception;

public class InvalidDecreeDataException extends RuntimeException {
    public InvalidDecreeDataException() {
        super();
    }

    public InvalidDecreeDataException(String message) {
        super(message);
    }

    public InvalidDecreeDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDecreeDataException(Throwable cause) {
        super(cause);
    }
}
