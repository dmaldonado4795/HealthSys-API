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
    private final IPatientService patientService;
    private final IDoctorService doctorService;
    private final IAppointmentService appointmentService;

    public AppointmentController(
            IPatientService patientService,
            IDoctorService doctorService,
            IAppointmentService appointmentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getAppointments() {
        Map<String, Object> response = new HashMap<>();
        List<AppointmentEntity> appointments = appointmentService.findAll();
        if (!appointments.isEmpty()) {
            response.put(ResponseHelper.DATA_KEY, appointments);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("appointments"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getAppointment(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<AppointmentEntity> appointment = appointmentService.findById(id);
        if (appointment.isPresent()) {
            response.put(ResponseHelper.DATA_KEY, appointment);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Appointment"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveAppointment(@Valid @RequestBody AppointmentDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                response.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                response.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            AppointmentEntity entity = appointmentService.save(MapperObjectUtil.toAppointment(0, dto));
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
    public ResponseEntity<Map<String, Object>> updateDoctor(@PathVariable long id, @Valid @RequestBody AppointmentDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<DoctorEntity> doctorEntity = doctorService.findById(dto.getDoctor().getDoctorId());
            if (doctorEntity.isEmpty()) {
                response.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("Doctor"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<PatientEntity> patientEntity = patientService.findById(dto.getPatient().getPatientId());
            if (patientEntity.isEmpty()) {
                response.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("Patient"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            AppointmentEntity entity = appointmentService.update(MapperObjectUtil.toAppointment(id, dto));
            response.put(ResponseHelper.ERROR_KEY, entity);
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
