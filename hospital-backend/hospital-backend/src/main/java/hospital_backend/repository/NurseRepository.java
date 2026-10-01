package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hospital_backend.entity.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Optional<Nurse> findByNurseNumber(String nurseNumber);
    Optional<Nurse> findByMobileNumber(String mobileNumber);
}
