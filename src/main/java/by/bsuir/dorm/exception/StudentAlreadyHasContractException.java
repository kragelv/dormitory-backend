package by.bsuir.dorm.exception;

public class StudentAlreadyHasContractException extends RuntimeException {
    public StudentAlreadyHasContractException() {
        super();
    }

    public StudentAlreadyHasContractException(String message) {
        super(message);
    }

    public StudentAlreadyHasContractException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyHasContractException(Throwable cause) {
        super(cause);
    }
}
