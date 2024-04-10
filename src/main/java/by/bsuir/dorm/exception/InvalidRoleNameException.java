package by.bsuir.dorm.exception;

public class InvalidRoleNameException extends RuntimeException {
    public InvalidRoleNameException() {
        super();
    }

    public InvalidRoleNameException(String message) {
        super(message);
    }

    public InvalidRoleNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRoleNameException(Throwable cause) {
        super(cause);
    }
}
