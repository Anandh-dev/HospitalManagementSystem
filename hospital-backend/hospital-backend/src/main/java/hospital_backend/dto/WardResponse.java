package hospital_backend.dto;

public class WardResponse {
    private Long wardId; private String wardName; private String wardType; private String status;
    public Long getWardId() { return wardId; } public void setWardId(Long v) { wardId = v; }
    public String getWardName() { return wardName; } public void setWardName(String v) { wardName = v; }
    public String getWardType() { return wardType; } public void setWardType(String v) { wardType = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
}