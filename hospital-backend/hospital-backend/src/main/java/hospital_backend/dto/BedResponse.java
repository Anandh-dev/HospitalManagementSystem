package hospital_backend.dto;

public class BedResponse {
    private Long bedId; private String bedNumber; private Long roomId; private String roomNumber; private Long wardId; private String wardName; private String status;
    public Long getBedId() { return bedId; } public void setBedId(Long v) { bedId = v; }
    public String getBedNumber() { return bedNumber; } public void setBedNumber(String v) { bedNumber = v; }
    public Long getRoomId() { return roomId; } public void setRoomId(Long v) { roomId = v; }
    public String getRoomNumber() { return roomNumber; } public void setRoomNumber(String v) { roomNumber = v; }
    public Long getWardId() { return wardId; } public void setWardId(Long v) { wardId = v; }
    public String getWardName() { return wardName; } public void setWardName(String v) { wardName = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
}