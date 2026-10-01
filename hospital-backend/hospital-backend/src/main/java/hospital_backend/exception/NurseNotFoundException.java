package hospital_backend.exception;

public class NurseNotFoundException extends RuntimeException {
    public NurseNotFoundException(String message) {
        super(message);
    }
}
