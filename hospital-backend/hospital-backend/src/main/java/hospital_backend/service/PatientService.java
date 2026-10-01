package hospital_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.PatientRequest;
import hospital_backend.dto.PatientResponse;
import hospital_backend.entity.Patient;
import hospital_backend.entity.PatientSequence;
import hospital_backend.exception.DuplicatePatientException;
import hospital_backend.exception.PatientNotFoundException;
import hospital_backend.repository.PatientRepository;
import hospital_backend.repository.PatientSequenceRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientSequenceRepository patientSequenceRepository;


    public PatientService(
            PatientRepository patientRepository,
            PatientSequenceRepository patientSequenceRepository) {

        this.patientRepository = patientRepository;
        this.patientSequenceRepository = patientSequenceRepository;
    }


    public List<PatientResponse> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    public PatientResponse getPatientById(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient with ID "
                                + patientId
                                + " not found"));

        return convertToResponse(patient);
    }


    public PatientResponse getPatientByNumber(
            String patientNumber) {

        Patient patient =
                patientRepository
                        .findByPatientNumber(patientNumber)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient "
                                        + patientNumber
                                        + " not found"));

        return convertToResponse(patient);
    }


    public PatientResponse getPatientByMobile(
            String mobileNumber) {

        Patient patient =
                patientRepository
                        .findByMobileNumber(mobileNumber)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient with mobile number "
                                        + mobileNumber
                                        + " not found"));

        return convertToResponse(patient);
    }


    @Transactional
    public PatientResponse createPatient(
            PatientRequest request) {

        if (patientRepository
                .findByMobileNumber(request.getMobileNumber())
                .isPresent()) {

            throw new DuplicatePatientException(
                    "A patient with this mobile number already exists");
        }


        PatientSequence sequence =
                patientSequenceRepository
                        .findById(1L)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Patient number sequence not configured"));


        Long number = sequence.getNextNumber();


        String patientNumber =
                String.format("P%06d", number);


        Patient patient = new Patient();

        patient.setPatientNumber(patientNumber);

        patient.setFullName(
                request.getFullName());

        patient.setDateOfBirth(
                request.getDateOfBirth());

        patient.setGender(
                request.getGender());

        patient.setMobileNumber(
                request.getMobileNumber());

        patient.setWhatsappNumber(
                request.getWhatsappNumber());

        patient.setEmail(
                request.getEmail());

        patient.setAddress(
                request.getAddress());


        sequence.setNextNumber(number + 1);

        patientSequenceRepository.save(sequence);


        Patient savedPatient =
                patientRepository.save(patient);


        return convertToResponse(savedPatient);
    }


    private PatientResponse convertToResponse(
            Patient patient) {

        PatientResponse response =
                new PatientResponse();

        response.setPatientId(
                patient.getPatientId());

        response.setPatientNumber(
                patient.getPatientNumber());

        response.setFullName(
                patient.getFullName());

        response.setDateOfBirth(
                patient.getDateOfBirth());

        response.setGender(
                patient.getGender());

        response.setMobileNumber(
                patient.getMobileNumber());

        response.setWhatsappNumber(
                patient.getWhatsappNumber());

        response.setEmail(
                patient.getEmail());

        response.setAddress(
                patient.getAddress());

        response.setCreatedAt(
                patient.getCreatedAt());

        response.setUpdatedAt(
                patient.getUpdatedAt());

        return response;
    }
}