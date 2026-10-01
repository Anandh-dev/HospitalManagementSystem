package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import hospital_backend.entity.AppointmentSequence;
import jakarta.persistence.LockModeType;

public interface AppointmentSequenceRepository extends JpaRepository<AppointmentSequence, Long> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AppointmentSequence> findById(Long id);
}
