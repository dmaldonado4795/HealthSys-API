package com.iDevWorks.HealthSys_API.web.dtos;

import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDto {
    @NotNull(message = "The parameter 'totalAmount' is required")
    private Double totalAmount;
    @NotNull(message = "The parameter 'date' is required")
    private LocalDate date;
    @NotNull(message = "The parameter 'appointment' is required")
    private AppointmentEntity appointment;
}
