package hospital_backend.exception;

public class InvalidSettingValueException extends RuntimeException {

    public InvalidSettingValueException(String message) {
        super(message);
    }
}