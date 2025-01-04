package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.web.dtos.DoctorDto;
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
@RequestMapping(path = "doctor")
public class DoctorController {
    private final IDoctorService service;

    public DoctorController(IDoctorService service) {
        this.service = service;
    }

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getDoctors() {
        Map<String, Object> response = new HashMap<>();
        List<DoctorEntity> doctors = service.findAll();
        if (!doctors.isEmpty()) {
            response.put(ResponseHelper.DATA_KEY, doctors);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.ERROR_KEY, "No registered doctors found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorById(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<DoctorEntity> doctor = service.findById(id);
        if (doctor.isPresent()) {
            response.put(ResponseHelper.ERROR_KEY, doctor.get());
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.ERROR_KEY, "Doctor not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveDoctor(@Valid @RequestBody DoctorDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            DoctorEntity entity = service.save(MapperObjectUtil.toDoctorEntity(0, dto));
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
    public ResponseEntity<Map<String, Object>> updateDoctor(@PathVariable long id, @Valid @RequestBody DoctorDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            DoctorEntity entity = service.update(MapperObjectUtil.toDoctorEntity(id, dto));
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
