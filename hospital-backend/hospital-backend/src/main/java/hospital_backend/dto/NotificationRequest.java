package hospital_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NotificationRequest {

    @NotBlank(message = "Notification title is required")
    @Size(max = 150, message = "Notification title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Notification message is required")
    private String message;

    @NotBlank(message = "Notification type is required")
    @Size(max = 30, message = "Notification type must not exceed 30 characters")
    private String type;

    @NotBlank(message = "Notification priority is required")
    @Size(max = 20, message = "Notification priority must not exceed 20 characters")
    private String priority;

    @NotBlank(message = "Recipient type is required")
    @Size(max = 30, message = "Recipient type must not exceed 30 characters")
    private String recipientType;

    private Long recipientId;

    @Size(max = 30, message = "Reference type must not exceed 30 characters")
    private String referenceType;

    private Long referenceId;

    public NotificationRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
}