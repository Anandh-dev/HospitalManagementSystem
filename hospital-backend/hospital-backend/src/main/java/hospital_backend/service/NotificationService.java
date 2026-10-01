package hospital_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.NotificationRequest;
import hospital_backend.dto.NotificationResponse;
import hospital_backend.entity.Notification;
import hospital_backend.exception.InvalidNotificationException;
import hospital_backend.exception.NotificationNotFoundException;
import hospital_backend.repository.NotificationRepository;

@Service
@Transactional
public class NotificationService {

    private static final String TYPE_APPOINTMENT = "APPOINTMENT";
    private static final String TYPE_OPD = "OPD";
    private static final String TYPE_IPD = "IPD";
    private static final String TYPE_SYSTEM = "SYSTEM";

    private static final String PRIORITY_LOW = "LOW";
    private static final String PRIORITY_NORMAL = "NORMAL";
    private static final String PRIORITY_HIGH = "HIGH";
    private static final String PRIORITY_URGENT = "URGENT";

    private static final String RECIPIENT_SYSTEM = "SYSTEM";
    private static final String RECIPIENT_PATIENT = "PATIENT";
    private static final String RECIPIENT_DOCTOR = "DOCTOR";
    private static final String RECIPIENT_NURSE = "NURSE";

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadNotifications() {
        return notificationRepository
                .findByReadFalseOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return notificationRepository.countByReadFalse();
    }

    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found: " + id));

        return toResponse(notification);
    }

    public NotificationResponse createNotification(NotificationRequest request) {
        String type = normalize(request.getType());
        String priority = normalize(request.getPriority());
        String recipientType = normalize(request.getRecipientType());

        validateType(type);
        validatePriority(priority);
        validateRecipientType(recipientType);
        validateRecipientId(recipientType, request.getRecipientId());
        validateReference(request.getReferenceType(), request.getReferenceId());

        Notification notification = new Notification(
                request.getTitle().trim(),
                request.getMessage().trim(),
                type,
                priority,
                recipientType,
                request.getRecipientId(),
                normalizeNullable(request.getReferenceType()),
                request.getReferenceId()
        );

        return toResponse(notificationRepository.save(notification));
    }

    public NotificationResponse markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found: " + id));

        if (!notification.isRead()) {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
            notification = notificationRepository.save(notification);
        }

        return toResponse(notification);
    }

    public int markAllAsRead() {
        List<Notification> unreadNotifications =
                notificationRepository.findByReadFalseOrderByCreatedAtDesc();

        LocalDateTime now = LocalDateTime.now();

        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
            notification.setReadAt(now);
        }

        if (!unreadNotifications.isEmpty()) {
            notificationRepository.saveAll(unreadNotifications);
        }

        return unreadNotifications.size();
    }

    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found: " + id));

        notificationRepository.delete(notification);
    }

    private void validateType(String type) {
        if (!TYPE_APPOINTMENT.equals(type)
                && !TYPE_OPD.equals(type)
                && !TYPE_IPD.equals(type)
                && !TYPE_SYSTEM.equals(type)) {

            throw new InvalidNotificationException(
                    "Unsupported notification type: " + type);
        }
    }

    private void validatePriority(String priority) {
        if (!PRIORITY_LOW.equals(priority)
                && !PRIORITY_NORMAL.equals(priority)
                && !PRIORITY_HIGH.equals(priority)
                && !PRIORITY_URGENT.equals(priority)) {

            throw new InvalidNotificationException(
                    "Unsupported notification priority: " + priority);
        }
    }

    private void validateRecipientType(String recipientType) {
        if (!RECIPIENT_SYSTEM.equals(recipientType)
                && !RECIPIENT_PATIENT.equals(recipientType)
                && !RECIPIENT_DOCTOR.equals(recipientType)
                && !RECIPIENT_NURSE.equals(recipientType)) {

            throw new InvalidNotificationException(
                    "Unsupported recipient type: " + recipientType);
        }
    }

    private void validateRecipientId(String recipientType, Long recipientId) {
        if (!RECIPIENT_SYSTEM.equals(recipientType) && recipientId == null) {
            throw new InvalidNotificationException(
                    "Recipient ID is required for " + recipientType);
        }

        if (RECIPIENT_SYSTEM.equals(recipientType) && recipientId != null) {
            throw new InvalidNotificationException(
                    "System notifications must not have a recipient ID");
        }
    }

    private void validateReference(String referenceType, Long referenceId) {
        if ((referenceType == null || referenceType.isBlank())
                && referenceId != null) {
            throw new InvalidNotificationException(
                    "Reference type is required when reference ID is provided");
        }

        if (referenceType != null && !referenceType.isBlank()
                && referenceId == null) {
            throw new InvalidNotificationException(
                    "Reference ID is required when reference type is provided");
        }
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidNotificationException(
                    "Notification type, priority and recipient type are required");
        }

        return value.trim().toUpperCase();
    }

    private String normalizeNullable(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim().toUpperCase();
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getPriority(),
                notification.getRecipientType(),
                notification.getRecipientId(),
                notification.getReferenceType(),
                notification.getReferenceId(),
                notification.isRead(),
                notification.getCreatedAt(),
                notification.getReadAt()
        );
    }
}