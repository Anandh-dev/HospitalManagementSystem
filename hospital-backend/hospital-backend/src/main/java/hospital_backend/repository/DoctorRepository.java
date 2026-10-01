package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hospital_backend.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByDoctorNumber(String doctorNumber);

    Optional<Doctor> findByMobileNumber(String mobileNumber);
}
