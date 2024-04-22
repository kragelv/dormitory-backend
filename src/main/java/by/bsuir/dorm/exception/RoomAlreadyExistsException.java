package by.bsuir.dorm.exception;

public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException() {
        super();
    }

    public RoomAlreadyExistsException(String message) {
        super(message);
    }

    public RoomAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoomAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
