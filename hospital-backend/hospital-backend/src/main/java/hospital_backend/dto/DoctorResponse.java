package hospital_backend.dto;

import java.time.LocalDateTime;

public class DoctorResponse {
    private Long doctorId; private String doctorNumber; private String fullName; private String specialization; private String department; private String mobileNumber; private String email; private String availability; private String status; private LocalDateTime createdAt; private LocalDateTime updatedAt;
    public Long getDoctorId() { return doctorId; } public void setDoctorId(Long value) { doctorId = value; }
    public String getDoctorNumber() { return doctorNumber; } public void setDoctorNumber(String value) { doctorNumber = value; }
    public String getFullName() { return fullName; } public void setFullName(String value) { fullName = value; }
    public String getSpecialization() { return specialization; } public void setSpecialization(String value) { specialization = value; }
    public String getDepartment() { return department; } public void setDepartment(String value) { department = value; }
    public String getMobileNumber() { return mobileNumber; } public void setMobileNumber(String value) { mobileNumber = value; }
    public String getEmail() { return email; } public void setEmail(String value) { email = value; }
    public String getAvailability() { return availability; } public void setAvailability(String value) { availability = value; }
    public String getStatus() { return status; } public void setStatus(String value) { status = value; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime value) { createdAt = value; }
    public LocalDateTime getUpdatedAt() { return updatedAt; } public void setUpdatedAt(LocalDateTime value) { updatedAt = value; }
}
