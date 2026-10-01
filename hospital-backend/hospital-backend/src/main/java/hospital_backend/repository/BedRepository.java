package hospital_backend.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import hospital_backend.entity.Bed;
import jakarta.persistence.LockModeType;
public interface BedRepository extends JpaRepository<Bed, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE) Optional<Bed> findLockedByBedId(Long bedId);
    List<Bed> findByStatus(String status);
    List<Bed> findByRoomRoomId(Long roomId);
    List<Bed> findByRoomWardWardId(Long wardId);
}