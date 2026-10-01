package hospital_backend.exception;

public class OPDVisitNotFoundException extends RuntimeException {
    public OPDVisitNotFoundException(String message) { super(message); }
}