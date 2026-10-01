package hospital_backend.exception;

public class InvalidOPDStatusException extends RuntimeException {
    public InvalidOPDStatusException(String message) { super(message); }
}