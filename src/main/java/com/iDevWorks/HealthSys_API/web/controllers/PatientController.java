package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.web.dtos.PatientDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "patient")
public class PatientController {
    private final IPatientService service;

    public PatientController(IPatientService service) {
        this.service = service;
    }

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getPatients() {
        Map<String, Object> response = new HashMap<>();
        List<PatientEntity> patients = service.findAll();
        if (!patients.isEmpty()) {
            response.put(ResponseHelper.DATA_KEY, patients);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.ERROR_KEY, "No registered patients found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getPatientById(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<PatientEntity> patient = service.findById(id);
        if (patient.isPresent()) {
            response.put(ResponseHelper.DATA_KEY, patient.get());
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.ERROR_KEY, "Patient not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> savePatient(@Valid @RequestBody PatientDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            PatientEntity entity = service.save(MapperObjectUtil.toPatientEntity(0, dto));
            response.put(ResponseHelper.DATA_KEY, entity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updatePatient(@PathVariable long id, @Valid @RequestBody PatientDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            PatientEntity entity = service.update(MapperObjectUtil.toPatientEntity(id, dto));
            response.put(ResponseHelper.DATA_KEY, entity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
