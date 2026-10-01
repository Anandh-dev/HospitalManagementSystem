package hospital_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SystemSettingRequest {

    @NotBlank(message = "Setting key is required")
    @Size(max = 100, message = "Setting key must not exceed 100 characters")
    private String settingKey;

    @NotBlank(message = "Setting value is required")
    private String settingValue;

    @NotBlank(message = "Setting type is required")
    @Size(max = 20, message = "Setting type must not exceed 20 characters")
    private String settingType;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    public SystemSettingRequest() {
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}