package hospital_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hospital_backend.dto.DoctorRequest;
import hospital_backend.dto.DoctorResponse;
import hospital_backend.entity.Doctor;
import hospital_backend.entity.DoctorSequence;
import hospital_backend.exception.DuplicateDoctorException;
import hospital_backend.repository.DoctorRepository;
import hospital_backend.repository.DoctorSequenceRepository;
import hospital_backend.service.DoctorService;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorSequenceRepository doctorSequenceRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void createDoctorShouldGenerateDoctorNumberAndPersistDoctor() {
        DoctorSequence sequence = new DoctorSequence();
        sequence.setId(1L);
        sequence.setNextNumber(1L);

        when(doctorSequenceRepository.findById(1L)).thenReturn(Optional.of(sequence));
        when(doctorRepository.findByMobileNumber("9876543210")).thenReturn(Optional.empty());
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> {
            Doctor doctor = invocation.getArgument(0);
            doctor.setDoctorId(1L);
            doctor.setDoctorNumber("D000001");
            return doctor;
        });

        DoctorRequest request = new DoctorRequest();
        request.setFullName("Dr. Alice Johnson");
        request.setSpecialization("Cardiology");
        request.setDepartment("Cardiology");
        request.setMobileNumber("9876543210");
        request.setEmail("alice@example.com");
        request.setAvailability("AVAILABLE");
        request.setStatus("AVAILABLE");

        DoctorResponse response = doctorService.createDoctor(request);

        assertEquals("D000001", response.getDoctorNumber());
        assertEquals("Dr. Alice Johnson", response.getFullName());
        verify(doctorSequenceRepository).save(sequence);
    }

    @Test
    void createDoctorShouldRejectDuplicateMobileNumber() {
        when(doctorRepository.findByMobileNumber("9876543210")).thenReturn(Optional.of(new Doctor()));

        DoctorRequest request = new DoctorRequest();
        request.setFullName("Dr. Bob Smith");
        request.setSpecialization("Neurology");
        request.setDepartment("Neurology");
        request.setMobileNumber("9876543210");
        request.setEmail("bob@example.com");
        request.setAvailability("AVAILABLE");
        request.setStatus("AVAILABLE");

        DuplicateDoctorException exception = assertThrows(
                DuplicateDoctorException.class,
                () -> doctorService.createDoctor(request)
        );

        assertTrue(exception.getMessage().contains("mobile number"));
    }
}
