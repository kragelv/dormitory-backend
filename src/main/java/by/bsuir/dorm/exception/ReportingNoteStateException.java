package by.bsuir.dorm.exception;

public class ReportingNoteStateException extends RuntimeException {
    public ReportingNoteStateException() {
        super();
    }

    public ReportingNoteStateException(String message) {
        super(message);
    }

    public ReportingNoteStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportingNoteStateException(Throwable cause) {
        super(cause);
    }
}
