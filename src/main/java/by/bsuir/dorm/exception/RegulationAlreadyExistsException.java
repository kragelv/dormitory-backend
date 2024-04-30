package by.bsuir.dorm.exception;

public class RegulationAlreadyExistsException extends RuntimeException {
    public RegulationAlreadyExistsException() {
        super();
    }

    public RegulationAlreadyExistsException(String message) {
        super(message);
    }

    public RegulationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegulationAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
