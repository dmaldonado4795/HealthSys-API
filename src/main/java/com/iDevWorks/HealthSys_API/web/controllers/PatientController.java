package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.utils.ObjectMappingUtil;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.web.dtos.PatientRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IPatientService service;
    final String DATA_KEY = "data";
    final String ERROR_KEY = "error";

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getPatients() {
        Map<String, Object> resp = new HashMap<>();
        List<PatientEntity> patients = service.findAll();
        if (!patients.isEmpty()) {
            resp.put(DATA_KEY, patients);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(DATA_KEY, "No registered patients found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getPatientById(@PathVariable long id) {
        Map<String, Object> resp = new HashMap<>();
        Optional<PatientEntity> patient = service.findById(id);
        if (patient.isPresent()) {
            resp.put(DATA_KEY, patient.get());
            return ResponseEntity.ok(resp);
        } else {
            resp.put(DATA_KEY, "Patient not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> savePatient(@Valid @RequestBody PatientRequestDTO dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            PatientEntity entity = service.save(ObjectMappingUtil.toPatientEntity(0, dto));
            resp.put(DATA_KEY, entity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updatePatient(@PathVariable long id, @Valid @RequestBody PatientRequestDTO dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            PatientEntity entity = service.save(ObjectMappingUtil.toPatientEntity(id, dto));
            resp.put(DATA_KEY, entity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }
}
