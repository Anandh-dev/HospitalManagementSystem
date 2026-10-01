package hospital_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OPDVisitRequest {
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    private Long appointmentId;
    @NotNull(message = "Visit date is required")
    @FutureOrPresent(message = "Visit date must not be in the past")
    private LocalDate visitDate;
    @Size(max = 500, message = "Symptoms must not exceed 500 characters")
    private String symptoms;
    @Size(max = 500, message = "Diagnosis must not exceed 500 characters")
    private String diagnosis;
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long value) { doctorId = value; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long value) { appointmentId = value; }
    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate value) { visitDate = value; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String value) { symptoms = value; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String value) { diagnosis = value; }
    public String getNotes() { return notes; }
    public void setNotes(String value) { notes = value; }
}