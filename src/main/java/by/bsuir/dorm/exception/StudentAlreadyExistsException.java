package by.bsuir.dorm.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException() {
        super();
    }

    public StudentAlreadyExistsException(String message) {
        super(message);
    }

    public StudentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
