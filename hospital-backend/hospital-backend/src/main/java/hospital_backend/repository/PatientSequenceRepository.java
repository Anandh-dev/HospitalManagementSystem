package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import hospital_backend.entity.PatientSequence;
import jakarta.persistence.LockModeType;

public interface PatientSequenceRepository
        extends JpaRepository<PatientSequence, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PatientSequence> findById(Long id);
}