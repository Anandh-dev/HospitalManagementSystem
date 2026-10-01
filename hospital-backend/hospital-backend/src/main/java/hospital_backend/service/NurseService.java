package hospital_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.NurseRequest;
import hospital_backend.dto.NurseResponse;
import hospital_backend.entity.Nurse;
import hospital_backend.entity.NurseSequence;
import hospital_backend.exception.DuplicateNurseException;
import hospital_backend.exception.NurseNotFoundException;
import hospital_backend.repository.NurseRepository;
import hospital_backend.repository.NurseSequenceRepository;

@Service
public class NurseService {

    private final NurseRepository nurseRepository;
    private final NurseSequenceRepository nurseSequenceRepository;

    public NurseService(NurseRepository nurseRepository, NurseSequenceRepository nurseSequenceRepository) {
        this.nurseRepository = nurseRepository;
        this.nurseSequenceRepository = nurseSequenceRepository;
    }

    public List<NurseResponse> getAllNurses() {
        return nurseRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    public NurseResponse getNurseById(Long nurseId) {
        Nurse nurse = nurseRepository.findById(nurseId)
                .orElseThrow(() -> new NurseNotFoundException("Nurse with ID " + nurseId + " not found"));
        return convertToResponse(nurse);
    }

    public NurseResponse getNurseByNumber(String nurseNumber) {
        Nurse nurse = nurseRepository.findByNurseNumber(nurseNumber)
                .orElseThrow(() -> new NurseNotFoundException("Nurse " + nurseNumber + " not found"));
        return convertToResponse(nurse);
    }

    public NurseResponse getNurseByMobile(String mobileNumber) {
        Nurse nurse = nurseRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new NurseNotFoundException("Nurse with mobile number " + mobileNumber + " not found"));
        return convertToResponse(nurse);
    }

    @Transactional
    public NurseResponse createNurse(NurseRequest request) {
        if (nurseRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            throw new DuplicateNurseException("A nurse with this mobile number already exists");
        }

        NurseSequence sequence = nurseSequenceRepository.findById(1L)
                .orElseGet(() -> {
                    NurseSequence newSequence = new NurseSequence();
                    newSequence.setId(1L);
                    newSequence.setNextNumber(1L);
                    return newSequence;
                });

        Long number = sequence.getNextNumber();
        String nurseNumber = String.format("N%06d", number);

        Nurse nurse = new Nurse();
        nurse.setNurseNumber(nurseNumber);
        nurse.setFullName(request.getFullName());
        nurse.setDepartment(request.getDepartment());
        nurse.setMobileNumber(request.getMobileNumber());
        nurse.setEmail(request.getEmail());
        nurse.setShift(request.getShift());
        nurse.setStatus(request.getStatus());

        sequence.setNextNumber(number + 1);
        nurseSequenceRepository.save(sequence);

        Nurse savedNurse = nurseRepository.save(nurse);
        return convertToResponse(savedNurse);
    }

    private NurseResponse convertToResponse(Nurse nurse) {
        NurseResponse response = new NurseResponse();
        response.setNurseId(nurse.getNurseId());
        response.setNurseNumber(nurse.getNurseNumber());
        response.setFullName(nurse.getFullName());
        response.setDepartment(nurse.getDepartment());
        response.setMobileNumber(nurse.getMobileNumber());
        response.setEmail(nurse.getEmail());
        response.setShift(nurse.getShift());
        response.setStatus(nurse.getStatus());
        response.setCreatedAt(nurse.getCreatedAt());
        response.setUpdatedAt(nurse.getUpdatedAt());
        return response;
    }
}
