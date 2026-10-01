package hospital_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OPDVisitResponse {
    private Long opdVisitId;
    private String opdNumber;
    private Long patientId;
    private String patientNumber;
    private String patientName;
    private Long doctorId;
    private String doctorNumber;
    private String doctorName;
    private Long appointmentId;
    private String appointmentNumber;
    private LocalDate visitDate;
    private Integer queueNumber;
    private LocalDateTime checkInTime;
    private LocalDateTime consultationStartTime;
    private LocalDateTime consultationEndTime;
    private String status;
    private String symptoms;
    private String diagnosis;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getOpdVisitId() { return opdVisitId; }
    public void setOpdVisitId(Long value) { opdVisitId = value; }
    public String getOpdNumber() { return opdNumber; }
    public void setOpdNumber(String value) { opdNumber = value; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public String getPatientNumber() { return patientNumber; }
    public void setPatientNumber(String value) { patientNumber = value; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String value) { patientName = value; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long value) { doctorId = value; }
    public String getDoctorNumber() { return doctorNumber; }
    public void setDoctorNumber(String value) { doctorNumber = value; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String value) { doctorName = value; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long value) { appointmentId = value; }
    public String getAppointmentNumber() { return appointmentNumber; }
    public void setAppointmentNumber(String value) { appointmentNumber = value; }
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