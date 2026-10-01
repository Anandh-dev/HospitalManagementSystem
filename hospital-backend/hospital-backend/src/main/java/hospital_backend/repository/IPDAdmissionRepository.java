package hospital_backend.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import hospital_backend.entity.IPDAdmission;
public interface IPDAdmissionRepository extends JpaRepository<IPDAdmission, Long> {
    Optional<IPDAdmission> findByIpdNumber(String ipdNumber);
    List<IPDAdmission> findByPatientPatientIdOrderByAdmissionDateDesc(Long patientId);
    List<IPDAdmission> findByAdmittingDoctorDoctorIdOrderByAdmissionDateDesc(Long doctorId);
    List<IPDAdmission> findByStatusOrderByAdmissionDateDesc(String status);
    List<IPDAdmission> findByAdmissionDateOrderByAdmissionTimeDesc(java.time.LocalDate date);
}