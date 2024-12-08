package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;
import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IAppointmentService;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.web.dtos.AppointmentDto;
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
@RequestMapping(path = "appointment")
public class AppointmentController {
    @Autowired
    private IPatientService patientService;
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getAppointments() {
        Map<String, Object> resp = new HashMap<>();
        List<AppointmentEntity> appointments = appointmentService.findAll();
        if (!appointments.isEmpty()) {
            resp.put(ResponseHelper.DATA_KEY, appointments);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("appointments"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getAppointment(@PathVariable long id) {
        Map<String, Object> resp = new HashMap<>();
        Optional<AppointmentEntity> appointment = appointmentService.findById(id);
        if (appointment.isPresent()) {
            resp.put(ResponseHelper.DATA_KEY, appointment);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Appointment"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveAppointment(@Valid @RequestBody AppointmentDto dto) {
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

            AppointmentEntity entity = appointmentService.save(MapperObjectUtil.toAppointment(0, dto));
            resp.put(ResponseHelper.DATA_KEY, entity);
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
    public ResponseEntity<Map<String, Object>> updateDoctor(@PathVariable long id, @Valid @RequestBody AppointmentDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            AppointmentEntity entity = appointmentService.update(MapperObjectUtil.toAppointment(id, dto));
            resp.put(ResponseHelper.ERROR_KEY, entity);
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
