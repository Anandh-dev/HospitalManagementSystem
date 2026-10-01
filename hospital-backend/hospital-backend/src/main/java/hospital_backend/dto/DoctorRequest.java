package hospital_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DoctorRequest {
    @NotBlank(message = "Full name is required") @Size(max = 100, message = "Full name cannot exceed 100 characters") private String fullName;
    @NotBlank(message = "Specialization is required") @Size(max = 100, message = "Specialization cannot exceed 100 characters") private String specialization;
    @NotBlank(message = "Department is required") @Size(max = 100, message = "Department cannot exceed 100 characters") private String department;
    @NotBlank(message = "Mobile number is required") @Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number must contain 10 to 15 digits") private String mobileNumber;
    @Email(message = "Please provide a valid email address") private String email;
    @NotBlank(message = "Availability is required") private String availability;
    @NotBlank(message = "Status is required") private String status;
    public String getFullName() { return fullName; } public void setFullName(String value) { fullName = value; }
    public String getSpecialization() { return specialization; } public void setSpecialization(String value) { specialization = value; }
    public String getDepartment() { return department; } public void setDepartment(String value) { department = value; }
    public String getMobileNumber() { return mobileNumber; } public void setMobileNumber(String value) { mobileNumber = value; }
    public String getEmail() { return email; } public void setEmail(String value) { email = value; }
    public String getAvailability() { return availability; } public void setAvailability(String value) { availability = value; }
    public String getStatus() { return status; } public void setStatus(String value) { status = value; }
}
