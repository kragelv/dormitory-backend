package by.bsuir.dorm.exception;

public class ReportingNoteNotFoundException extends RuntimeException {
    public ReportingNoteNotFoundException() {
        super();
    }

    public ReportingNoteNotFoundException(String message) {
        super(message);
    }

    public ReportingNoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportingNoteNotFoundException(Throwable cause) {
        super(cause);
    }
}
