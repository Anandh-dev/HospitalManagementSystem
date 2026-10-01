package hospital_backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.dto.BedResponse;
import hospital_backend.dto.IPDAdmissionRequest;
import hospital_backend.dto.IPDAdmissionResponse;
import hospital_backend.dto.IPDDischargeRequest;
import hospital_backend.dto.IPDInventoryRequest;
import hospital_backend.entity.Bed;
import hospital_backend.entity.Doctor;
import hospital_backend.entity.IPDAdmission;
import hospital_backend.entity.IPDSequence;
import hospital_backend.entity.OPDVisit;
import hospital_backend.entity.Patient;
import hospital_backend.entity.Room;
import hospital_backend.entity.Ward;
import hospital_backend.exception.BedNotAvailableException;
import hospital_backend.exception.DoctorNotFoundException;
import hospital_backend.exception.InvalidIPDStatusException;
import hospital_backend.exception.IPDAdmissionNotFoundException;
import hospital_backend.exception.OPDVisitNotFoundException;
import hospital_backend.exception.PatientNotFoundException;
import hospital_backend.repository.BedRepository;
import hospital_backend.repository.DoctorRepository;
import hospital_backend.repository.IPDAdmissionRepository;
import hospital_backend.repository.IPDSequenceRepository;
import hospital_backend.repository.OPDVisitRepository;
import hospital_backend.repository.PatientRepository;
import hospital_backend.repository.RoomRepository;
import hospital_backend.repository.WardRepository;

@Service
public class IPDAdmissionService {
    private final IPDAdmissionRepository admissionRepository;
    private final IPDSequenceRepository sequenceRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final OPDVisitRepository opdVisitRepository;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final WardRepository wardRepository;

    public IPDAdmissionService(IPDAdmissionRepository admissionRepository, IPDSequenceRepository sequenceRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, OPDVisitRepository opdVisitRepository, BedRepository bedRepository, RoomRepository roomRepository, WardRepository wardRepository) {
        this.admissionRepository = admissionRepository; this.sequenceRepository = sequenceRepository; this.patientRepository = patientRepository; this.doctorRepository = doctorRepository; this.opdVisitRepository = opdVisitRepository; this.bedRepository = bedRepository; this.roomRepository = roomRepository; this.wardRepository = wardRepository;
    }

    @Transactional(readOnly = true) public List<IPDAdmissionResponse> getAll() { return admissionRepository.findAll().stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public IPDAdmissionResponse getById(Long id) { return toResponse(findAdmission(id)); }
    @Transactional(readOnly = true) public IPDAdmissionResponse getByNumber(String number) { return toResponse(admissionRepository.findByIpdNumber(number).orElseThrow(() -> new IPDAdmissionNotFoundException("IPD admission " + number + " not found"))); }
    @Transactional(readOnly = true) public List<IPDAdmissionResponse> getByPatient(Long id) { return admissionRepository.findByPatientPatientIdOrderByAdmissionDateDesc(id).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public List<IPDAdmissionResponse> getByDoctor(Long id) { return admissionRepository.findByAdmittingDoctorDoctorIdOrderByAdmissionDateDesc(id).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public List<IPDAdmissionResponse> getByStatus(String status) { return admissionRepository.findByStatusOrderByAdmissionDateDesc(status).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public List<BedResponse> getBeds() { return bedRepository.findAll().stream().map(this::toBedResponse).toList(); }
    @Transactional(readOnly = true) public List<BedResponse> getAvailableBeds() { return bedRepository.findByStatus("AVAILABLE").stream().map(this::toBedResponse).toList(); }
    @Transactional(readOnly = true) public List<BedResponse> getBedsByWard(Long wardId) { return bedRepository.findByRoomWardWardId(wardId).stream().map(this::toBedResponse).toList(); }
    @Transactional(readOnly = true) public List<hospital_backend.dto.WardResponse> getWards() { return wardRepository.findAll().stream().map(this::toWardResponse).toList(); }
    @Transactional(readOnly = true) public List<hospital_backend.dto.RoomResponse> getRoomsByWard(Long wardId) { return roomRepository.findByWardWardId(wardId).stream().map(this::toRoomResponse).toList(); }

    @Transactional
    public IPDAdmissionResponse admit(IPDAdmissionRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() -> new PatientNotFoundException("Patient with ID " + request.getPatientId() + " not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> new DoctorNotFoundException("Doctor with ID " + request.getDoctorId() + " not found"));
        OPDVisit opd = request.getOpdVisitId() == null ? null : opdVisitRepository.findById(request.getOpdVisitId()).orElseThrow(() -> new OPDVisitNotFoundException("OPD visit with ID " + request.getOpdVisitId() + " not found"));
        if (opd != null && !opd.getPatient().getPatientId().equals(patient.getPatientId())) throw new IllegalArgumentException("OPD patient does not match the selected patient");
        Bed bed = bedRepository.findLockedByBedId(request.getBedId()).orElseThrow(() -> new BedNotAvailableException("Bed with ID " + request.getBedId() + " not found"));
        if (!"AVAILABLE".equals(bed.getStatus())) throw new BedNotAvailableException("Bed " + bed.getBedNumber() + " is already occupied");
        IPDSequence sequence = sequenceRepository.findById(1L).orElseGet(() -> { IPDSequence value = new IPDSequence(); value.setId(1L); value.setNextNumber(1L); return value; });
        Long number = sequence.getNextNumber(); sequence.setNextNumber(number + 1); sequenceRepository.save(sequence);
        IPDAdmission admission = new IPDAdmission(); admission.setIpdNumber(String.format("IPD%06d", number)); admission.setPatient(patient); admission.setAdmittingDoctor(doctor); admission.setReferringOPDVisit(opd); admission.setBed(bed); admission.setAdmissionDate(request.getAdmissionDate()); admission.setAdmissionTime(LocalTime.now().withNano(0).toString()); admission.setAdmissionReason(request.getAdmissionReason()); admission.setDiagnosis(request.getDiagnosis()); admission.setTreatmentPlan(request.getTreatmentPlan()); admission.setStatus("ADMITTED"); bed.setStatus("OCCUPIED"); bedRepository.save(bed); return toResponse(admissionRepository.save(admission));
    }

    @Transactional
    public IPDAdmissionResponse discharge(Long id, IPDDischargeRequest request) { IPDAdmission admission = findAdmission(id); if (!"ADMITTED".equals(admission.getStatus())) throw new InvalidIPDStatusException("IPD admission " + admission.getIpdNumber() + " is already " + admission.getStatus()); admission.setDischargeDate(LocalDate.now()); admission.setDischargeTime(LocalTime.now().withNano(0).toString()); admission.setDischargeSummary(request.getDischargeSummary()); admission.setStatus("DISCHARGED"); Bed bed = bedRepository.findLockedByBedId(admission.getBed().getBedId()).orElseThrow(() -> new BedNotAvailableException("Admission bed not found")); bed.setStatus("AVAILABLE"); bedRepository.save(bed); return toResponse(admissionRepository.save(admission)); }

    @Transactional public BedResponse createInventory(IPDInventoryRequest request) { Ward ward = wardRepository.findAll().stream().filter(value -> value.getWardName().equalsIgnoreCase(request.getWardName())).findFirst().orElseGet(() -> { Ward value = new Ward(); value.setWardName(request.getWardName()); value.setWardType(request.getWardType()); value.setStatus("ACTIVE"); return wardRepository.save(value); }); Room room = roomRepository.findByWardWardId(ward.getWardId()).stream().filter(value -> value.getRoomNumber().equalsIgnoreCase(request.getRoomNumber())).findFirst().orElseGet(() -> { Room value = new Room(); value.setRoomNumber(request.getRoomNumber()); value.setWard(ward); value.setStatus("ACTIVE"); return roomRepository.save(value); }); Bed bed = new Bed(); bed.setBedNumber(request.getBedNumber()); bed.setRoom(room); bed.setStatus("AVAILABLE"); return toBedResponse(bedRepository.save(bed)); }

    private IPDAdmission findAdmission(Long id) { return admissionRepository.findById(id).orElseThrow(() -> new IPDAdmissionNotFoundException("IPD admission with ID " + id + " not found")); }
    private IPDAdmissionResponse toResponse(IPDAdmission value) { Bed bed = value.getBed(); Room room = bed.getRoom(); Ward ward = room.getWard(); IPDAdmissionResponse response = new IPDAdmissionResponse(); response.setIpdAdmissionId(value.getIpdAdmissionId()); response.setIpdNumber(value.getIpdNumber()); response.setPatientId(value.getPatient().getPatientId()); response.setPatientNumber(value.getPatient().getPatientNumber()); response.setPatientName(value.getPatient().getFullName()); response.setDoctorId(value.getAdmittingDoctor().getDoctorId()); response.setDoctorNumber(value.getAdmittingDoctor().getDoctorNumber()); response.setDoctorName(value.getAdmittingDoctor().getFullName()); if (value.getReferringOPDVisit() != null) { response.setOpdVisitId(value.getReferringOPDVisit().getOpdVisitId()); response.setOpdNumber(value.getReferringOPDVisit().getOpdNumber()); } response.setWardId(ward.getWardId()); response.setWardName(ward.getWardName()); response.setRoomId(room.getRoomId()); response.setRoomNumber(room.getRoomNumber()); response.setBedId(bed.getBedId()); response.setBedNumber(bed.getBedNumber()); response.setBedStatus(bed.getStatus()); response.setAdmissionDate(value.getAdmissionDate()); response.setAdmissionTime(value.getAdmissionTime()); response.setDischargeDate(value.getDischargeDate()); response.setDischargeTime(value.getDischargeTime()); response.setAdmissionReason(value.getAdmissionReason()); response.setDiagnosis(value.getDiagnosis()); response.setTreatmentPlan(value.getTreatmentPlan()); response.setDischargeSummary(value.getDischargeSummary()); response.setStatus(value.getStatus()); response.setCreatedAt(value.getCreatedAt()); response.setUpdatedAt(value.getUpdatedAt()); return response; }
    private BedResponse toBedResponse(Bed value) { Room room = value.getRoom(); Ward ward = room.getWard(); BedResponse response = new BedResponse(); response.setBedId(value.getBedId()); response.setBedNumber(value.getBedNumber()); response.setRoomId(room.getRoomId()); response.setRoomNumber(room.getRoomNumber()); response.setWardId(ward.getWardId()); response.setWardName(ward.getWardName()); response.setStatus(value.getStatus()); return response; }
    private hospital_backend.dto.WardResponse toWardResponse(Ward value) { hospital_backend.dto.WardResponse response = new hospital_backend.dto.WardResponse(); response.setWardId(value.getWardId()); response.setWardName(value.getWardName()); response.setWardType(value.getWardType()); response.setStatus(value.getStatus()); return response; }
    private hospital_backend.dto.RoomResponse toRoomResponse(Room value) { hospital_backend.dto.RoomResponse response = new hospital_backend.dto.RoomResponse(); response.setRoomId(value.getRoomId()); response.setRoomNumber(value.getRoomNumber()); response.setWardId(value.getWard().getWardId()); response.setWardName(value.getWard().getWardName()); response.setStatus(value.getStatus()); return response; }
}