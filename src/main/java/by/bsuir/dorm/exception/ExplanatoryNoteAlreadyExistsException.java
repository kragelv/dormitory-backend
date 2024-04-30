package by.bsuir.dorm.exception;

public class ExplanatoryNoteAlreadyExistsException extends RuntimeException {
    public ExplanatoryNoteAlreadyExistsException() {
        super();
    }

    public ExplanatoryNoteAlreadyExistsException(String message) {
        super(message);
    }

    public ExplanatoryNoteAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExplanatoryNoteAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
