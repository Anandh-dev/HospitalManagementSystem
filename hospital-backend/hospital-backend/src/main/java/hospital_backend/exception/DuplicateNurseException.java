package hospital_backend.exception;

public class DuplicateNurseException extends RuntimeException {
    public DuplicateNurseException(String message) {
        super(message);
    }
}
