package hospital_backend.exception;

public class DuplicatePatientException extends RuntimeException {

    public DuplicatePatientException(String message) {
        super(message);
    }
}