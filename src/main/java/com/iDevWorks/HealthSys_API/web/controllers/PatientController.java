package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "patient")
public class PatientController {
    @Autowired
    private IPatientService service;

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getPatients() {
        Map<String, Object> resp = new HashMap<>();
        List<PatientEntity> patients = service.findAll();
        final String claim = "data";
        if (!patients.isEmpty()) {
            resp.put(claim, patients);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(claim, "No registered patients found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }
}
