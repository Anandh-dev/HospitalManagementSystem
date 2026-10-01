package hospital_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hospital_backend.entity.OPDVisit;

public interface OPDVisitRepository extends JpaRepository<OPDVisit, Long> {
    List<OPDVisit> findByVisitDateOrderByQueueNumberAsc(LocalDate visitDate);
    List<OPDVisit> findByPatientPatientIdOrderByVisitDateDesc(Long patientId);
    List<OPDVisit> findByDoctorDoctorIdOrderByVisitDateDesc(Long doctorId);
    List<OPDVisit> findByStatusOrderByVisitDateDesc(String status);
}