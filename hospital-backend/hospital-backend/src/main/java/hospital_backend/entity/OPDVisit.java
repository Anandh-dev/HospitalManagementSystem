package hospital_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "opd_visit")
public class OPDVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opd_visit_id")
    private Long opdVisitId;

    @Column(name = "opd_number", nullable = false, unique = true, length = 20)
    private String opdNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "queue_number", nullable = false)
    private Integer queueNumber;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "consultation_start_time")
    private LocalDateTime consultationStartTime;

    @Column(name = "consultation_end_time")
    private LocalDateTime consultationEndTime;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 500)
    private String symptoms;

    @Column(length = 500)
    private String diagnosis;

    @Column(length = 500)
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { LocalDateTime now = LocalDateTime.now(); createdAt = now; updatedAt = now; }
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public Long getOpdVisitId() { return opdVisitId; }
    public void setOpdVisitId(Long value) { opdVisitId = value; }
    public String getOpdNumber() { return opdNumber; }
    public void setOpdNumber(String value) { opdNumber = value; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient value) { patient = value; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor value) { doctor = value; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment value) { appointment = value; }
    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate value) { visitDate = value; }
    public Integer getQueueNumber() { return queueNumber; }
    public void setQueueNumber(Integer value) { queueNumber = value; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime value) { checkInTime = value; }
    public LocalDateTime getConsultationStartTime() { return consultationStartTime; }
    public void setConsultationStartTime(LocalDateTime value) { consultationStartTime = value; }
    public LocalDateTime getConsultationEndTime() { return consultationEndTime; }
    public void setConsultationEndTime(LocalDateTime value) { consultationEndTime = value; }
    public String getStatus() { return status; }
    public void setStatus(String value) { status = value; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String value) { symptoms = value; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String value) { diagnosis = value; }
    public String getNotes() { return notes; }
    public void setNotes(String value) { notes = value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { createdAt = value; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime value) { updatedAt = value; }
}