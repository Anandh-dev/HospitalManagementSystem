package hospital_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import hospital_backend.entity.Ward;
public interface WardRepository extends JpaRepository<Ward, Long> { }