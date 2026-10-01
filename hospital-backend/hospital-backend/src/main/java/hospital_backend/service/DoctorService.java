package hospital_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.DoctorRequest;
import hospital_backend.dto.DoctorResponse;
import hospital_backend.entity.Doctor;
import hospital_backend.entity.DoctorSequence;
import hospital_backend.exception.DuplicateDoctorException;
import hospital_backend.exception.DoctorNotFoundException;
import hospital_backend.repository.DoctorRepository;
import hospital_backend.repository.DoctorSequenceRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorSequenceRepository doctorSequenceRepository;

    public DoctorService(
            DoctorRepository doctorRepository,
            DoctorSequenceRepository doctorSequenceRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorSequenceRepository = doctorSequenceRepository;
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public DoctorResponse getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor with ID " + doctorId + " not found"));
        return convertToResponse(doctor);
    }

    public DoctorResponse getDoctorByNumber(String doctorNumber) {
        Doctor doctor = doctorRepository.findByDoctorNumber(doctorNumber)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor " + doctorNumber + " not found"));
        return convertToResponse(doctor);
    }

    public DoctorResponse getDoctorByMobile(String mobileNumber) {
        Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor with mobile number " + mobileNumber + " not found"));
        return convertToResponse(doctor);
    }

    @Transactional
    public DoctorResponse createDoctor(DoctorRequest request) {
        if (doctorRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            throw new DuplicateDoctorException(
                    "A doctor with this mobile number already exists");
        }

        DoctorSequence sequence = doctorSequenceRepository.findById(1L)
                .orElseGet(() -> {
                    DoctorSequence newSequence = new DoctorSequence();
                    newSequence.setId(1L);
                    newSequence.setNextNumber(1L);
                    return newSequence;
                });

        Long number = sequence.getNextNumber();
        String doctorNumber = String.format("D%06d", number);

        Doctor doctor = new Doctor();
        doctor.setDoctorNumber(doctorNumber);
        doctor.setFullName(request.getFullName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setDepartment(request.getDepartment());
        doctor.setMobileNumber(request.getMobileNumber());
        doctor.setEmail(request.getEmail());
        doctor.setAvailability(request.getAvailability());
        doctor.setStatus(request.getStatus());

        sequence.setNextNumber(number + 1);
        doctorSequenceRepository.save(sequence);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToResponse(savedDoctor);
    }

    private DoctorResponse convertToResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setDoctorId(doctor.getDoctorId());
        response.setDoctorNumber(doctor.getDoctorNumber());
        response.setFullName(doctor.getFullName());
        response.setSpecialization(doctor.getSpecialization());
        response.setDepartment(doctor.getDepartment());
        response.setMobileNumber(doctor.getMobileNumber());
        response.setEmail(doctor.getEmail());
        response.setAvailability(doctor.getAvailability());
        response.setStatus(doctor.getStatus());
        response.setCreatedAt(doctor.getCreatedAt());
        response.setUpdatedAt(doctor.getUpdatedAt());
        return response;
    }
}
