package hospital_backend.dto;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private String title;
    private String message;
    private String type;
    private String priority;
    private String recipientType;
    private Long recipientId;
    private String referenceType;
    private Long referenceId;
    private boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    public NotificationResponse() {
    }

    public NotificationResponse(
            Long id,
            String title,
            String message,
            String type,
            String priority,
            String recipientType,
            Long recipientId,
            String referenceType,
            Long referenceId,
            boolean read,
            LocalDateTime createdAt,
            LocalDateTime readAt) {

        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
        this.recipientType = recipientType;
        this.recipientId = recipientId;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.read = read;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }
}