package by.bsuir.dorm.exception;

public class RoleNotCompatibleException extends RuntimeException {
    public RoleNotCompatibleException() {
        super();
    }

    public RoleNotCompatibleException(String message) {
        super(message);
    }

    public RoleNotCompatibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotCompatibleException(Throwable cause) {
        super(cause);
    }
}
