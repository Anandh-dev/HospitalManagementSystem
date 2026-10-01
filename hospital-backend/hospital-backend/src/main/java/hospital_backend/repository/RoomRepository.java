package hospital_backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import hospital_backend.entity.Room;
public interface RoomRepository extends JpaRepository<Room, Long> { List<Room> findByWardWardId(Long wardId); }