package hospital_backend.dto;

import java.time.LocalDateTime;

public class NurseResponse {
    private Long nurseId;
    private String nurseNumber;
    private String fullName;
    private String department;
    private String mobileNumber;
    private String email;
    private String shift;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getNurseId() { return nurseId; }
    public void setNurseId(Long nurseId) { this.nurseId = nurseId; }
    public String getNurseNumber() { return nurseNumber; }
    public void setNurseNumber(String nurseNumber) { this.nurseNumber = nurseNumber; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
