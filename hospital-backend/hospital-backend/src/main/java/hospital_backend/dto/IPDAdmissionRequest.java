package hospital_backend.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class IPDAdmissionRequest {
    @NotNull private Long patientId;
    @NotNull private Long doctorId;
    private Long opdVisitId;
    @NotNull @FutureOrPresent private LocalDate admissionDate;
    @NotNull private Long bedId;
    @NotBlank @Size(max = 500) private String admissionReason;
    @Size(max = 500) private String diagnosis;
    @Size(max = 500) private String treatmentPlan;
    public Long getPatientId() { return patientId; } public void setPatientId(Long value) { patientId = value; }
    public Long getDoctorId() { return doctorId; } public void setDoctorId(Long value) { doctorId = value; }
    public Long getOpdVisitId() { return opdVisitId; } public void setOpdVisitId(Long value) { opdVisitId = value; }
    public LocalDate getAdmissionDate() { return admissionDate; } public void setAdmissionDate(LocalDate value) { admissionDate = value; }
    public Long getBedId() { return bedId; } public void setBedId(Long value) { bedId = value; }
    public String getAdmissionReason() { return admissionReason; } public void setAdmissionReason(String value) { admissionReason = value; }
    public String getDiagnosis() { return diagnosis; } public void setDiagnosis(String value) { diagnosis = value; }
    public String getTreatmentPlan() { return treatmentPlan; } public void setTreatmentPlan(String value) { treatmentPlan = value; }
}