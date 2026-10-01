package hospital_backend.dto;

public class SystemSettingResponse {

    private Long id;
    private String settingKey;
    private String settingValue;
    private String settingType;
    private String description;

    public SystemSettingResponse() {
    }

    public SystemSettingResponse(Long id,
                                 String settingKey,
                                 String settingValue,
                                 String settingType,
                                 String description) {
        this.id = id;
        this.settingKey = settingKey;
        this.settingValue = settingValue;
        this.settingType = settingType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public String getSettingType() {
        return settingType;
    }

    public String getDescription() {
        return description;
    }
}