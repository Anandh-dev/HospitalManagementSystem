package hospital_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital_backend.dto.DoctorRequest;
import hospital_backend.dto.DoctorResponse;
import hospital_backend.service.DoctorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = {
        "http://localhost:5173",
    "http://127.0.0.1:5173",
    "http://localhost:5174",
    "http://127.0.0.1:5174"
})
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping("/number/{doctorNumber}")
    public ResponseEntity<DoctorResponse> getDoctorByNumber(@PathVariable String doctorNumber) {
        return ResponseEntity.ok(doctorService.getDoctorByNumber(doctorNumber));
    }

    @GetMapping("/mobile/{mobileNumber}")
    public ResponseEntity<DoctorResponse> getDoctorByMobile(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(doctorService.getDoctorByMobile(mobileNumber));
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        DoctorResponse response = doctorService.createDoctor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
