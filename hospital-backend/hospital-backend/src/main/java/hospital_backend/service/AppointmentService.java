package hospital_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.AppointmentRequest;
import hospital_backend.dto.AppointmentResponse;
import hospital_backend.entity.Appointment;
import hospital_backend.entity.AppointmentSequence;
import hospital_backend.entity.Doctor;
import hospital_backend.entity.Patient;
import hospital_backend.exception.AppointmentNotFoundException;
import hospital_backend.repository.AppointmentRepository;
import hospital_backend.repository.AppointmentSequenceRepository;
import hospital_backend.repository.DoctorRepository;
import hospital_backend.repository.PatientRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentSequenceRepository appointmentSequenceRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AppointmentSequenceRepository appointmentSequenceRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentSequenceRepository = appointmentSequenceRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    public AppointmentResponse getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + appointmentId + " not found"));
        return convertToResponse(appointment);
    }

    public AppointmentResponse getAppointmentByNumber(String appointmentNumber) {
        return appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getAppointmentNumber().equals(appointmentNumber))
                .findFirst()
                .map(this::convertToResponse)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment " + appointmentNumber + " not found"));
    }

    public List<AppointmentResponse> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date).stream().map(this::convertToResponse).toList();
    }

    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientPatientId(patientId).stream().map(this::convertToResponse).toList();
    }

    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorDoctorId(doctorId).stream().map(this::convertToResponse).toList();
    }

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + request.getDoctorId()));

        AppointmentSequence sequence = appointmentSequenceRepository.findById(1L)
                .orElseGet(() -> {
                    AppointmentSequence newSequence = new AppointmentSequence();
                    newSequence.setId(1L);
                    newSequence.setNextNumber(1L);
                    return newSequence;
                });

        Long number = sequence.getNextNumber();
        String appointmentNumber = String.format("A%06d", number);

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(appointmentNumber);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());
        appointment.setNotes(request.getNotes());

        sequence.setNextNumber(number + 1);
        appointmentSequenceRepository.save(sequence);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToResponse(savedAppointment);
    }

    private AppointmentResponse convertToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(appointment.getAppointmentId());
        response.setAppointmentNumber(appointment.getAppointmentNumber());
        response.setPatientId(appointment.getPatient().getPatientId());
        response.setPatientName(appointment.getPatient().getFullName());
        response.setDoctorId(appointment.getDoctor().getDoctorId());
        response.setDoctorName(appointment.getDoctor().getFullName());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setReason(appointment.getReason());
        response.setStatus(appointment.getStatus());
        response.setNotes(appointment.getNotes());
        response.setCreatedAt(appointment.getCreatedAt());
        response.setUpdatedAt(appointment.getUpdatedAt());
        return response;
    }
}
