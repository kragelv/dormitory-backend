package by.bsuir.dorm.exception;

public class CookieNotFoundException extends RuntimeException {
    public CookieNotFoundException() {
        super();
    }

    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieNotFoundException(Throwable cause) {
        super(cause);
    }
}
