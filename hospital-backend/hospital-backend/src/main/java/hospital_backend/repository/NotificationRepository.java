package hospital_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hospital_backend.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByOrderByCreatedAtDesc();

    List<Notification> findByReadFalseOrderByCreatedAtDesc();

    long countByReadFalse();

    List<Notification> findByRecipientTypeOrderByCreatedAtDesc(String recipientType);

    List<Notification> findByRecipientTypeAndRecipientIdOrderByCreatedAtDesc(
            String recipientType,
            Long recipientId);

    List<Notification> findByTypeOrderByCreatedAtDesc(String type);

    List<Notification> findByReferenceTypeAndReferenceId(
            String referenceType,
            Long referenceId);
}