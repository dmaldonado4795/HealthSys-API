package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.entities.MedicalHistoryEntity;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.domain.services.IMedicalHistoryService;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.web.dtos.MedicalHistoryDto;
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
@RequestMapping(path = "medical-history")
public class MedicalHistoryController {
    private final IMedicalHistoryService medicalHistoryService;
    private final IPatientService patientService;
    private final IDoctorService doctorService;

    public MedicalHistoryController(
            IMedicalHistoryService medicalHistoryService,
            IPatientService patientService,
            IDoctorService doctorService) {
        this.medicalHistoryService = medicalHistoryService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getMedicalHistories() {
        Map<String, Object> response = new HashMap<>();
        List<MedicalHistoryEntity> medicalHistories = medicalHistoryService.findAll();
        if (!medicalHistories.isEmpty()) {
            response.put(ResponseHelper.DATA_KEY, medicalHistories);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.NoRegisteredItem("medical histories"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getMedicalHistory(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<MedicalHistoryEntity> medicalHistory = medicalHistoryService.findById(id);
        if (medicalHistory.isPresent()) {
            response.put(ResponseHelper.DATA_KEY, medicalHistory);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Medical history"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveMedicalHistory(@Valid @RequestBody MedicalHistoryDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            MedicalHistoryEntity medicalHistoryEntity = medicalHistoryService.save(MapperObjectUtil.toMedicalHistory(0, dto));
            response.put(ResponseHelper.DATA_KEY, medicalHistoryEntity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.MESSAGE_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updateMedicalHistory(@PathVariable long id, @Valid @RequestBody MedicalHistoryDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            MedicalHistoryEntity medicalHistoryEntity = medicalHistoryService.update(MapperObjectUtil.toMedicalHistory(id, dto));
            response.put(ResponseHelper.DATA_KEY, medicalHistoryEntity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.MESSAGE_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
