package by.bsuir.dorm.exception;

public class GroupAlreadyExistsException extends RuntimeException {
    public GroupAlreadyExistsException() {
        super();
    }

    public GroupAlreadyExistsException(String message) {
        super(message);
    }

    public GroupAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
