package hospital_backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital_backend.dto.OPDVisitRequest;
import hospital_backend.dto.OPDVisitResponse;
import hospital_backend.service.OPDVisitService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/opd")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:5174", "http://127.0.0.1:5174"})
public class OPDController {
    private final OPDVisitService opdVisitService;

    public OPDController(OPDVisitService opdVisitService) { this.opdVisitService = opdVisitService; }

    @GetMapping
    public ResponseEntity<List<OPDVisitResponse>> getAll() { return ResponseEntity.ok(opdVisitService.getAllVisits()); }

    @GetMapping("/{id}")
    public ResponseEntity<OPDVisitResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(opdVisitService.getVisitById(id)); }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<OPDVisitResponse>> getByDate(@PathVariable String date) { return ResponseEntity.ok(opdVisitService.getVisitsByDate(LocalDate.parse(date))); }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<OPDVisitResponse>> getByPatient(@PathVariable Long patientId) { return ResponseEntity.ok(opdVisitService.getVisitsByPatient(patientId)); }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<OPDVisitResponse>> getByDoctor(@PathVariable Long doctorId) { return ResponseEntity.ok(opdVisitService.getVisitsByDoctor(doctorId)); }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OPDVisitResponse>> getByStatus(@PathVariable String status) { return ResponseEntity.ok(opdVisitService.getVisitsByStatus(status)); }

    @PostMapping
    public ResponseEntity<OPDVisitResponse> create(@Valid @RequestBody OPDVisitRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(opdVisitService.createVisit(request)); }

    @PutMapping("/{id}/check-in")
    public ResponseEntity<OPDVisitResponse> checkIn(@PathVariable Long id) { return ResponseEntity.ok(opdVisitService.checkIn(id)); }

    @PutMapping("/{id}/start-consultation")
    public ResponseEntity<OPDVisitResponse> startConsultation(@PathVariable Long id) { return ResponseEntity.ok(opdVisitService.startConsultation(id)); }

    @PutMapping("/{id}/complete")
    public ResponseEntity<OPDVisitResponse> complete(@PathVariable Long id) { return ResponseEntity.ok(opdVisitService.complete(id)); }
}