package hospital_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital_backend.dto.SystemSettingRequest;
import hospital_backend.dto.SystemSettingResponse;
import hospital_backend.service.SystemSettingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController {

    private final SystemSettingService systemSettingService;

    public SystemSettingController(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @GetMapping
    public ResponseEntity<List<SystemSettingResponse>> getAllSettings() {
        return ResponseEntity.ok(systemSettingService.getAllSettings());
    }

    @GetMapping("/{key}")
    public ResponseEntity<SystemSettingResponse> getSetting(
            @PathVariable String key) {

        return ResponseEntity.ok(
                systemSettingService.getSetting(key)
        );
    }

    @PostMapping
    public ResponseEntity<SystemSettingResponse> createSetting(
            @Valid @RequestBody SystemSettingRequest request) {

        SystemSettingResponse response =
                systemSettingService.createSetting(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{key}")
    public ResponseEntity<SystemSettingResponse> updateSetting(
            @PathVariable String key,
            @Valid @RequestBody SystemSettingRequest request) {

        return ResponseEntity.ok(
                systemSettingService.updateSetting(key, request)
        );
    }
}