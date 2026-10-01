package hospital_backend.exception;

public class InvalidNotificationException extends RuntimeException {

    public InvalidNotificationException(String message) {
        super(message);
    }
}