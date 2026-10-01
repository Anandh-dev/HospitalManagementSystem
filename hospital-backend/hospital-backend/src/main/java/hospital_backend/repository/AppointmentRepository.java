package hospital_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hospital_backend.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
    List<Appointment> findByPatientPatientId(Long patientId);
    List<Appointment> findByDoctorDoctorId(Long doctorId);
}
