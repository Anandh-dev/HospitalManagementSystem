package hospital_backend.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import hospital_backend.entity.IPDSequence;
import jakarta.persistence.LockModeType;
public interface IPDSequenceRepository extends JpaRepository<IPDSequence, Long> {
    @Override @Lock(LockModeType.PESSIMISTIC_WRITE) Optional<IPDSequence> findById(Long id);
}