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
@Table(name = "ipd_admission")
public class IPDAdmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ipd_admission_id") private Long ipdAdmissionId;
    @Column(name = "ipd_number", nullable = false, unique = true, length = 20) private String ipdNumber;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "patient_id", nullable = false) private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "doctor_id", nullable = false) private Doctor admittingDoctor;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "opd_visit_id") private OPDVisit referringOPDVisit;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "bed_id", nullable = false) private Bed bed;
    @Column(name = "admission_date", nullable = false) private LocalDate admissionDate;
    @Column(name = "admission_time", nullable = false) private String admissionTime;
    @Column(name = "discharge_date") private LocalDate dischargeDate;
    @Column(name = "discharge_time") private String dischargeTime;
    @Column(name = "admission_reason", nullable = false, length = 500) private String admissionReason;
    @Column(length = 500) private String diagnosis;
    @Column(name = "treatment_plan", length = 500) private String treatmentPlan;
    @Column(name = "discharge_summary", length = 500) private String dischargeSummary;
    @Column(nullable = false, length = 30) private String status;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { LocalDateTime now = LocalDateTime.now(); createdAt = now; updatedAt = now; }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
    public Long getIpdAdmissionId() { return ipdAdmissionId; } public void setIpdAdmissionId(Long value) { ipdAdmissionId = value; }
    public String getIpdNumber() { return ipdNumber; } public void setIpdNumber(String value) { ipdNumber = value; }
    public Patient getPatient() { return patient; } public void setPatient(Patient value) { patient = value; }
    public Doctor getAdmittingDoctor() { return admittingDoctor; } public void setAdmittingDoctor(Doctor value) { admittingDoctor = value; }
    public OPDVisit getReferringOPDVisit() { return referringOPDVisit; } public void setReferringOPDVisit(OPDVisit value) { referringOPDVisit = value; }
    public Bed getBed() { return bed; } public void setBed(Bed value) { bed = value; }
    public LocalDate getAdmissionDate() { return admissionDate; } public void setAdmissionDate(LocalDate value) { admissionDate = value; }
    public String getAdmissionTime() { return admissionTime; } public void setAdmissionTime(String value) { admissionTime = value; }
    public LocalDate getDischargeDate() { return dischargeDate; } public void setDischargeDate(LocalDate value) { dischargeDate = value; }
    public void setDischargeTime(String value) { dischargeTime = value; } public String getDischargeTime() { return dischargeTime; }
    public String getAdmissionReason() { return admissionReason; } public void setAdmissionReason(String value) { admissionReason = value; }
    public String getDiagnosis() { return diagnosis; } public void setDiagnosis(String value) { diagnosis = value; }
    public String getTreatmentPlan() { return treatmentPlan; } public void setTreatmentPlan(String value) { treatmentPlan = value; }
    public String getDischargeSummary() { return dischargeSummary; } public void setDischargeSummary(String value) { dischargeSummary = value; }
    public String getStatus() { return status; } public void setStatus(String value) { status = value; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime value) { createdAt = value; }
    public LocalDateTime getUpdatedAt() { return updatedAt; } public void setUpdatedAt(LocalDateTime value) { updatedAt = value; }
}