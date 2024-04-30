package by.bsuir.dorm.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException() {
        super();
    }

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupNotFoundException(Throwable cause) {
        super(cause);
    }
}
