package by.bsuir.dorm.exception;

public class GroupStateException extends RuntimeException {
    public GroupStateException() {
        super();
    }

    public GroupStateException(String message) {
        super(message);
    }

    public GroupStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupStateException(Throwable cause) {
        super(cause);
    }
}
