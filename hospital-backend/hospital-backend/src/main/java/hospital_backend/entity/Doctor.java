package hospital_backend.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "doctor_id") private Long doctorId;
    @Column(name = "doctor_number", nullable = false, unique = true, length = 20) private String doctorNumber;
    @Column(name = "full_name", nullable = false, length = 100) private String fullName;
    @Column(nullable = false, length = 100) private String specialization;
    @Column(nullable = false, length = 100) private String department;
    @Column(name = "mobile_number", nullable = false, unique = true, length = 15) private String mobileNumber;
    @Column(length = 150) private String email;
    @Column(nullable = false, length = 30) private String availability;
    @Column(nullable = false, length = 30) private String status;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { LocalDateTime now = LocalDateTime.now(); createdAt = now; updatedAt = now; }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
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
