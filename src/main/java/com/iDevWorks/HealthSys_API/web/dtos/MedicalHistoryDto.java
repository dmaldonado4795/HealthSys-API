package com.iDevWorks.HealthSys_API.web.dtos;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalHistoryDto {
    @NotNull(message = "The parameter 'description' is required")
    private String description;
    @NotNull(message = "The parameter 'patient' is required")
    private PatientEntity patient;
    @NotNull(message = "The parameter 'doctor' is required")
    private DoctorEntity doctor;
}
