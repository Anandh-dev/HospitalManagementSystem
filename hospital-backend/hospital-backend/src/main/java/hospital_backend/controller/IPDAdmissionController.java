package hospital_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hospital_backend.dto.*;
import hospital_backend.service.IPDAdmissionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ipd")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:5174", "http://127.0.0.1:5174"})
public class IPDAdmissionController {
    private final IPDAdmissionService service;
    public IPDAdmissionController(IPDAdmissionService service) { this.service = service; }
    @GetMapping public ResponseEntity<List<IPDAdmissionResponse>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<IPDAdmissionResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @GetMapping("/number/{number}") public ResponseEntity<IPDAdmissionResponse> getByNumber(@PathVariable String number) { return ResponseEntity.ok(service.getByNumber(number)); }
    @GetMapping("/patient/{id}") public ResponseEntity<List<IPDAdmissionResponse>> getByPatient(@PathVariable Long id) { return ResponseEntity.ok(service.getByPatient(id)); }
    @GetMapping("/doctor/{id}") public ResponseEntity<List<IPDAdmissionResponse>> getByDoctor(@PathVariable Long id) { return ResponseEntity.ok(service.getByDoctor(id)); }
    @GetMapping("/status/{status}") public ResponseEntity<List<IPDAdmissionResponse>> getByStatus(@PathVariable String status) { return ResponseEntity.ok(service.getByStatus(status)); }
    @GetMapping("/beds") public ResponseEntity<List<BedResponse>> getBeds() { return ResponseEntity.ok(service.getBeds()); }
    @GetMapping("/beds/available") public ResponseEntity<List<BedResponse>> getAvailableBeds() { return ResponseEntity.ok(service.getAvailableBeds()); }
    @GetMapping("/beds/ward/{wardId}") public ResponseEntity<List<BedResponse>> getBedsByWard(@PathVariable Long wardId) { return ResponseEntity.ok(service.getBedsByWard(wardId)); }
    @GetMapping("/wards") public ResponseEntity<List<WardResponse>> getWards() { return ResponseEntity.ok(service.getWards()); }
    @GetMapping("/rooms/ward/{wardId}") public ResponseEntity<List<RoomResponse>> getRooms(@PathVariable Long wardId) { return ResponseEntity.ok(service.getRoomsByWard(wardId)); }
    @PostMapping("/inventory") public ResponseEntity<BedResponse> createInventory(@Valid @RequestBody IPDInventoryRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.createInventory(request)); }
    @PostMapping public ResponseEntity<IPDAdmissionResponse> admit(@Valid @RequestBody IPDAdmissionRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.admit(request)); }
    @PutMapping("/{id}/discharge") public ResponseEntity<IPDAdmissionResponse> discharge(@PathVariable Long id, @Valid @RequestBody IPDDischargeRequest request) { return ResponseEntity.ok(service.discharge(id, request)); }
}