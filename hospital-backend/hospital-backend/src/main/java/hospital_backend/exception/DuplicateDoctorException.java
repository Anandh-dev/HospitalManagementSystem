package hospital_backend.exception;

public class DuplicateDoctorException extends RuntimeException {

    public DuplicateDoctorException(String message) {
        super(message);
    }
}
