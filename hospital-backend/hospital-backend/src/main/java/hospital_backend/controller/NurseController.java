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

import hospital_backend.dto.NurseRequest;
import hospital_backend.dto.NurseResponse;
import hospital_backend.service.NurseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:5174", "http://127.0.0.1:5174"})
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping
    public ResponseEntity<List<NurseResponse>> getAllNurses() {
        return ResponseEntity.ok(nurseService.getAllNurses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NurseResponse> getNurseById(@PathVariable Long id) {
        return ResponseEntity.ok(nurseService.getNurseById(id));
    }

    @GetMapping("/number/{nurseNumber}")
    public ResponseEntity<NurseResponse> getNurseByNumber(@PathVariable String nurseNumber) {
        return ResponseEntity.ok(nurseService.getNurseByNumber(nurseNumber));
    }

    @GetMapping("/mobile/{mobileNumber}")
    public ResponseEntity<NurseResponse> getNurseByMobile(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(nurseService.getNurseByMobile(mobileNumber));
    }

    @PostMapping
    public ResponseEntity<NurseResponse> createNurse(@Valid @RequestBody NurseRequest request) {
        NurseResponse response = nurseService.createNurse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
