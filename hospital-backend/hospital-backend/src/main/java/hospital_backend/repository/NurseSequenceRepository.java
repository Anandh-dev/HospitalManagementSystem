package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import hospital_backend.entity.NurseSequence;
import jakarta.persistence.LockModeType;

public interface NurseSequenceRepository extends JpaRepository<NurseSequence, Long> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<NurseSequence> findById(Long id);
}
