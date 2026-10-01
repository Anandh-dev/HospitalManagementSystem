package hospital_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IPDAdmissionResponse {
    private Long ipdAdmissionId; private String ipdNumber; private Long patientId; private String patientNumber; private String patientName; private Long doctorId; private String doctorNumber; private String doctorName; private Long opdVisitId; private String opdNumber; private Long wardId; private String wardName; private Long roomId; private String roomNumber; private Long bedId; private String bedNumber; private String bedStatus; private LocalDate admissionDate; private String admissionTime; private LocalDate dischargeDate; private String dischargeTime; private String admissionReason; private String diagnosis; private String treatmentPlan; private String dischargeSummary; private String status; private LocalDateTime createdAt; private LocalDateTime updatedAt;
    public Long getIpdAdmissionId() { return ipdAdmissionId; } public void setIpdAdmissionId(Long v) { ipdAdmissionId = v; }
    public String getIpdNumber() { return ipdNumber; } public void setIpdNumber(String v) { ipdNumber = v; }
    public Long getPatientId() { return patientId; } public void setPatientId(Long v) { patientId = v; }
    public String getPatientNumber() { return patientNumber; } public void setPatientNumber(String v) { patientNumber = v; }
    public String getPatientName() { return patientName; } public void setPatientName(String v) { patientName = v; }
    public Long getDoctorId() { return doctorId; } public void setDoctorId(Long v) { doctorId = v; }
    public String getDoctorNumber() { return doctorNumber; } public void setDoctorNumber(String v) { doctorNumber = v; }
    public String getDoctorName() { return doctorName; } public void setDoctorName(String v) { doctorName = v; }
    public Long getOpdVisitId() { return opdVisitId; } public void setOpdVisitId(Long v) { opdVisitId = v; }
    public String getOpdNumber() { return opdNumber; } public void setOpdNumber(String v) { opdNumber = v; }
    public Long getWardId() { return wardId; } public void setWardId(Long v) { wardId = v; }
    public String getWardName() { return wardName; } public void setWardName(String v) { wardName = v; }
    public Long getRoomId() { return roomId; } public void setRoomId(Long v) { roomId = v; }
    public String getRoomNumber() { return roomNumber; } public void setRoomNumber(String v) { roomNumber = v; }
    public Long getBedId() { return bedId; } public void setBedId(Long v) { bedId = v; }
    public String getBedNumber() { return bedNumber; } public void setBedNumber(String v) { bedNumber = v; }
    public String getBedStatus() { return bedStatus; } public void setBedStatus(String v) { bedStatus = v; }
    public LocalDate getAdmissionDate() { return admissionDate; } public void setAdmissionDate(LocalDate v) { admissionDate = v; }
    public String getAdmissionTime() { return admissionTime; } public void setAdmissionTime(String v) { admissionTime = v; }
    public LocalDate getDischargeDate() { return dischargeDate; } public void setDischargeDate(LocalDate v) { dischargeDate = v; }
    public String getDischargeTime() { return dischargeTime; } public void setDischargeTime(String v) { dischargeTime = v; }
    public String getAdmissionReason() { return admissionReason; } public void setAdmissionReason(String v) { admissionReason = v; }
    public String getDiagnosis() { return diagnosis; } public void setDiagnosis(String v) { diagnosis = v; }
    public String getTreatmentPlan() { return treatmentPlan; } public void setTreatmentPlan(String v) { treatmentPlan = v; }
    public String getDischargeSummary() { return dischargeSummary; } public void setDischargeSummary(String v) { dischargeSummary = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime v) { createdAt = v; }
    public LocalDateTime getUpdatedAt() { return updatedAt; } public void setUpdatedAt(LocalDateTime v) { updatedAt = v; }
}