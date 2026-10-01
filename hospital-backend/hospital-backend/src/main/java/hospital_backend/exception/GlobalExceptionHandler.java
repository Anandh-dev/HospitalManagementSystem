package hospital_backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFound(
            PatientNotFoundException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDoctorNotFound(
            DoctorNotFoundException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicatePatientException.class)
    public ResponseEntity<Map<String, String>> handleDuplicatePatient(
            DuplicatePatientException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(DuplicateDoctorException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateDoctor(
            DuplicateDoctorException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(NurseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNurseNotFound(
            NurseNotFoundException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicateNurseException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateNurse(
            DuplicateNurseException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAppointmentNotFound(
            AppointmentNotFoundException exception) {

        Map<String, String> response = new HashMap<>();
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

        @ExceptionHandler(OPDVisitNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleOPDVisitNotFound(OPDVisitNotFoundException exception) {
                Map<String, String> response = new HashMap<>();
                response.put("message", exception.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler({InvalidOPDStatusException.class, OPDValidationException.class})
        public ResponseEntity<Map<String, String>> handleOPDValidation(RuntimeException exception) {
                Map<String, String> response = new HashMap<>();
                response.put("message", exception.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

        @ExceptionHandler(IPDAdmissionNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleIPDNotFound(IPDAdmissionNotFoundException exception) {
                Map<String, String> response = new HashMap<>();
                response.put("message", exception.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(BedNotAvailableException.class)
        public ResponseEntity<Map<String, String>> handleBedConflict(BedNotAvailableException exception) {
                Map<String, String> response = new HashMap<>();
                response.put("message", exception.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        @ExceptionHandler({InvalidIPDStatusException.class, IllegalArgumentException.class})
        public ResponseEntity<Map<String, String>> handleIPDBusinessValidation(RuntimeException exception) {
                Map<String, String> response = new HashMap<>();
                response.put("message", exception.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        @ExceptionHandler(SettingNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleSettingNotFound(
                SettingNotFoundException exception) {

            Map<String, String> response = new HashMap<>();
            response.put("message", exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        @ExceptionHandler(InvalidSettingValueException.class)
        public ResponseEntity<Map<String, String>> handleInvalidSettingValue(
                InvalidSettingValueException exception) {

            Map<String, String> response = new HashMap<>();
            response.put("message", exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        
        @ExceptionHandler(DuplicateSettingException.class)
        public ResponseEntity<Map<String, String>> handleDuplicateSetting(
                DuplicateSettingException exception) {

            Map<String, String> response = new HashMap<>();
            response.put("message", exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }
        
        @ExceptionHandler(NotificationNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleNotificationNotFound(
                NotificationNotFoundException exception) {

            Map<String, String> response = new HashMap<>();
            response.put("message", exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        @ExceptionHandler(InvalidNotificationException.class)
        public ResponseEntity<Map<String, String>> handleInvalidNotification(
                InvalidNotificationException exception) {

            Map<String, String> response = new HashMap<>();
            response.put("message", exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
}