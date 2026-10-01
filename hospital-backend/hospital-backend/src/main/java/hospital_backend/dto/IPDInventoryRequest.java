package hospital_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class IPDInventoryRequest {
    @NotBlank private String wardName;
    @NotBlank private String wardType;
    @NotBlank private String roomNumber;
    @NotBlank private String bedNumber;
    public String getWardName() { return wardName; } public void setWardName(String value) { wardName = value; }
    public String getWardType() { return wardType; } public void setWardType(String value) { wardType = value; }
    public String getRoomNumber() { return roomNumber; } public void setRoomNumber(String value) { roomNumber = value; }
    public String getBedNumber() { return bedNumber; } public void setBedNumber(String value) { bedNumber = value; }
}