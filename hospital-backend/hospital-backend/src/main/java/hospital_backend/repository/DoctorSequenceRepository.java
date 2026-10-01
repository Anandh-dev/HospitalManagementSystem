package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import hospital_backend.entity.DoctorSequence;
import jakarta.persistence.LockModeType;

public interface DoctorSequenceRepository extends JpaRepository<DoctorSequence, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DoctorSequence> findById(Long id);
}
