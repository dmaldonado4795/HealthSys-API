package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.utils.ObjectMappingUtil;
import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.web.dtos.DoctorRequestDTO;
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
@RequestMapping(path = "doctor")
public class DoctorController {
    @Autowired
    private IDoctorService service;
    public final String DATA_KEY = "data";
    public final String ERROR_KEY = "error";

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getDoctors() {
        Map<String, Object> resp = new HashMap<>();
        List<DoctorEntity> doctors = service.findAll();
        if (!doctors.isEmpty()) {
            resp.put(DATA_KEY, doctors);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ERROR_KEY, "No registered doctors found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorById(@PathVariable long id) {
        Map<String, Object> resp = new HashMap<>();
        Optional<DoctorEntity> doctor = service.findById(id);
        if (doctor.isPresent()) {
            resp.put(DATA_KEY, doctor.get());
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ERROR_KEY, "Doctor not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveDoctor(@Valid @RequestBody DoctorRequestDTO dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            DoctorEntity entity = service.save(ObjectMappingUtil.toDoctorEntity(0, dto));
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
    public ResponseEntity<Map<String, Object>> updateDoctor(@PathVariable long id, @Valid @RequestBody DoctorRequestDTO dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            DoctorEntity entity = service.update(ObjectMappingUtil.toDoctorEntity(id, dto));
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
