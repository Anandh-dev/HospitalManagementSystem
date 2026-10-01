package hospital_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IPDDischargeRequest {
    @NotBlank @Size(max = 500) private String dischargeSummary;
    public String getDischargeSummary() { return dischargeSummary; }
    public void setDischargeSummary(String value) { dischargeSummary = value; }
}