package by.bsuir.dorm.exception;

public class RoomStateException extends RuntimeException {
    public RoomStateException() {
        super();
    }

    public RoomStateException(String message) {
        super(message);
    }

    public RoomStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoomStateException(Throwable cause) {
        super(cause);
    }
}
