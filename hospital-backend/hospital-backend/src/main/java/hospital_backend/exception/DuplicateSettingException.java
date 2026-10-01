package hospital_backend.exception;

public class DuplicateSettingException extends RuntimeException {

    public DuplicateSettingException(String message) {
        super(message);
    }
}