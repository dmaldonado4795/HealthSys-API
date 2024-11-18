package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.ObjectMappingUtil;
import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.entities.MedicalHistoryEntity;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.domain.services.IMedicalHistoryService;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.web.dtos.MedicalHistoryDto;
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
@RequestMapping(path = "medical-history")
public class MedicalHistoryController {
    @Autowired
    private IMedicalHistoryService medicalHistoryService;
    @Autowired
    private IPatientService patientService;
    @Autowired
    private IDoctorService doctorService;

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getMedicalHistories() {
        Map<String, Object> resp = new HashMap<>();
        List<MedicalHistoryEntity> medicalHistories = medicalHistoryService.findAll();
        if (!medicalHistories.isEmpty()) {
            resp.put(ResponseHelper.DATA_KEY, medicalHistories);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("medical histories"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getMedicalHistory(@PathVariable long id) {
        Map<String, Object> resp = new HashMap<>();
        Optional<MedicalHistoryEntity> medicalHistory = medicalHistoryService.findById(id);
        if (medicalHistory.isPresent()) {
            resp.put(ResponseHelper.DATA_KEY, medicalHistory);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Medical history"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveMedicalHistory(@Valid @RequestBody MedicalHistoryDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            MedicalHistoryEntity medicalHistoryEntity = medicalHistoryService.save(ObjectMappingUtil.toMedicalHistory(0, dto));
            resp.put(ResponseHelper.DATA_KEY, medicalHistoryEntity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updateMedicalHistory(@PathVariable long id, @Valid @RequestBody MedicalHistoryDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            MedicalHistoryEntity medicalHistoryEntity = medicalHistoryService.update(ObjectMappingUtil.toMedicalHistory(id, dto));
            resp.put(ResponseHelper.DATA_KEY, medicalHistoryEntity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }
}
