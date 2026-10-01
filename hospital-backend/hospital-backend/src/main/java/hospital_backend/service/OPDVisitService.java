package hospital_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.OPDVisitRequest;
import hospital_backend.dto.OPDVisitResponse;
import hospital_backend.entity.Appointment;
import hospital_backend.entity.Doctor;
import hospital_backend.entity.OPDSequence;
import hospital_backend.entity.OPDVisit;
import hospital_backend.entity.Patient;
import hospital_backend.exception.AppointmentNotFoundException;
import hospital_backend.exception.DoctorNotFoundException;
import hospital_backend.exception.InvalidOPDStatusException;
import hospital_backend.exception.OPDValidationException;
import hospital_backend.exception.OPDVisitNotFoundException;
import hospital_backend.exception.PatientNotFoundException;
import hospital_backend.repository.AppointmentRepository;
import hospital_backend.repository.DoctorRepository;
import hospital_backend.repository.OPDSequenceRepository;
import hospital_backend.repository.OPDVisitRepository;
import hospital_backend.repository.PatientRepository;

@Service
public class OPDVisitService {
    private static final String OPD_SEQUENCE = "OPD";

    private final OPDVisitRepository opdVisitRepository;
    private final OPDSequenceRepository opdSequenceRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public OPDVisitService(OPDVisitRepository opdVisitRepository,
                           OPDSequenceRepository opdSequenceRepository,
                           PatientRepository patientRepository,
                           DoctorRepository doctorRepository,
                           AppointmentRepository appointmentRepository) {
        this.opdVisitRepository = opdVisitRepository;
        this.opdSequenceRepository = opdSequenceRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public List<OPDVisitResponse> getAllVisits() {
        return opdVisitRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OPDVisitResponse getVisitById(Long id) {
        return toResponse(findVisit(id));
    }

    @Transactional(readOnly = true)
    public List<OPDVisitResponse> getVisitsByDate(LocalDate date) {
        return opdVisitRepository.findByVisitDateOrderByQueueNumberAsc(date).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<OPDVisitResponse> getVisitsByPatient(Long patientId) {
        return opdVisitRepository.findByPatientPatientIdOrderByVisitDateDesc(patientId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<OPDVisitResponse> getVisitsByDoctor(Long doctorId) {
        return opdVisitRepository.findByDoctorDoctorIdOrderByVisitDateDesc(doctorId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<OPDVisitResponse> getVisitsByStatus(String status) {
        return opdVisitRepository.findByStatusOrderByVisitDateDesc(status).stream().map(this::toResponse).toList();
    }

    @Transactional
    public OPDVisitResponse createVisit(OPDVisitRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + request.getPatientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with ID " + request.getDoctorId() + " not found"));

        Appointment appointment = null;
        if (request.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + request.getAppointmentId() + " not found"));
            if (!appointment.getPatient().getPatientId().equals(patient.getPatientId())) {
                throw new OPDValidationException("Appointment patient does not match the selected patient");
            }
            if (!appointment.getDoctor().getDoctorId().equals(doctor.getDoctorId())) {
                throw new OPDValidationException("Appointment doctor does not match the selected doctor");
            }
        }

        Long opdNumber = nextNumber(OPD_SEQUENCE);
        Long queueNumber = nextNumber("QUEUE_" + request.getVisitDate());

        OPDVisit visit = new OPDVisit();
        visit.setOpdNumber(String.format("OPD%06d", opdNumber));
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setAppointment(appointment);
        visit.setVisitDate(request.getVisitDate());
        visit.setQueueNumber(Math.toIntExact(queueNumber));
        visit.setStatus("WAITING");
        visit.setSymptoms(request.getSymptoms());
        visit.setDiagnosis(request.getDiagnosis());
        visit.setNotes(request.getNotes());
        return toResponse(opdVisitRepository.save(visit));
    }

    @Transactional
    public OPDVisitResponse checkIn(Long id) {
        OPDVisit visit = findVisit(id);
        requireStatus(visit, "WAITING");
        visit.setCheckInTime(LocalDateTime.now());
        visit.setStatus("IN_CONSULTATION");
        return toResponse(opdVisitRepository.save(visit));
    }

    @Transactional
    public OPDVisitResponse startConsultation(Long id) {
        OPDVisit visit = findVisit(id);
        requireStatus(visit, "IN_CONSULTATION");
        if (visit.getConsultationStartTime() != null) {
            throw new InvalidOPDStatusException("Consultation has already started");
        }
        visit.setConsultationStartTime(LocalDateTime.now());
        return toResponse(opdVisitRepository.save(visit));
    }

    @Transactional
    public OPDVisitResponse complete(Long id) {
        OPDVisit visit = findVisit(id);
        requireStatus(visit, "IN_CONSULTATION");
        if (visit.getConsultationStartTime() == null) {
            throw new InvalidOPDStatusException("Consultation must be started before it can be completed");
        }
        visit.setConsultationEndTime(LocalDateTime.now());
        visit.setStatus("COMPLETED");
        return toResponse(opdVisitRepository.save(visit));
    }

    private Long nextNumber(String sequenceId) {
        OPDSequence sequence = opdSequenceRepository.findById(sequenceId).orElseGet(() -> {
            OPDSequence created = new OPDSequence();
            created.setId(sequenceId);
            created.setNextNumber(1L);
            return created;
        });
        Long number = sequence.getNextNumber();
        sequence.setNextNumber(number + 1);
        opdSequenceRepository.save(sequence);
        return number;
    }

    private OPDVisit findVisit(Long id) {
        return opdVisitRepository.findById(id)
                .orElseThrow(() -> new OPDVisitNotFoundException("OPD visit with ID " + id + " not found"));
    }

    private void requireStatus(OPDVisit visit, String expected) {
        if (!expected.equals(visit.getStatus())) {
            throw new InvalidOPDStatusException("OPD visit " + visit.getOpdNumber() + " is " + visit.getStatus() + ", expected " + expected);
        }
    }

    private OPDVisitResponse toResponse(OPDVisit visit) {
        OPDVisitResponse response = new OPDVisitResponse();
        response.setOpdVisitId(visit.getOpdVisitId());
        response.setOpdNumber(visit.getOpdNumber());
        response.setPatientId(visit.getPatient().getPatientId());
        response.setPatientNumber(visit.getPatient().getPatientNumber());
        response.setPatientName(visit.getPatient().getFullName());
        response.setDoctorId(visit.getDoctor().getDoctorId());
        response.setDoctorNumber(visit.getDoctor().getDoctorNumber());
        response.setDoctorName(visit.getDoctor().getFullName());
        if (visit.getAppointment() != null) {
            response.setAppointmentId(visit.getAppointment().getAppointmentId());
            response.setAppointmentNumber(visit.getAppointment().getAppointmentNumber());
        }
        response.setVisitDate(visit.getVisitDate());
        response.setQueueNumber(visit.getQueueNumber());
        response.setCheckInTime(visit.getCheckInTime());
        response.setConsultationStartTime(visit.getConsultationStartTime());
        response.setConsultationEndTime(visit.getConsultationEndTime());
        response.setStatus(visit.getStatus());
        response.setSymptoms(visit.getSymptoms());
        response.setDiagnosis(visit.getDiagnosis());
        response.setNotes(visit.getNotes());
        response.setCreatedAt(visit.getCreatedAt());
        response.setUpdatedAt(visit.getUpdatedAt());
        return response;
    }
}