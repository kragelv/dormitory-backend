package by.bsuir.dorm.exception;

public class RegulationStateException extends RuntimeException{
    public RegulationStateException() {
        super();
    }

    public RegulationStateException(String message) {
        super(message);
    }

    public RegulationStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegulationStateException(Throwable cause) {
        super(cause);
    }
}
