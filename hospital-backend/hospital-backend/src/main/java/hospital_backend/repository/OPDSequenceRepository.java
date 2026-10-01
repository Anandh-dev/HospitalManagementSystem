package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import hospital_backend.entity.OPDSequence;
import jakarta.persistence.LockModeType;

public interface OPDSequenceRepository extends JpaRepository<OPDSequence, String> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OPDSequence> findById(String id);
}