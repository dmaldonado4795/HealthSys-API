package com.iDevWorks.HealthSys_API.web.dtos;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    @NotNull(message = "The parameter 'date' is required")
    private LocalDateTime date;
    @NotNull(message = "The parameter 'reason' is required")
    private String reason;
    @NotNull(message = "The parameter 'patient' is required")
    private PatientEntity patient;
    @NotNull(message = "The parameter 'doctor' is required")
    private DoctorEntity doctor;
}
