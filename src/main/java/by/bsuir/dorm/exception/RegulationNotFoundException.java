package by.bsuir.dorm.exception;

public class RegulationNotFoundException extends RuntimeException {
    public RegulationNotFoundException() {
        super();
    }

    public RegulationNotFoundException(String message) {
        super(message);
    }

    public RegulationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegulationNotFoundException(Throwable cause) {
        super(cause);
    }
}
