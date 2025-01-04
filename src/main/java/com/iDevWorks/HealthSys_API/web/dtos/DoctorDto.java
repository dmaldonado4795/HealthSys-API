package com.iDevWorks.HealthSys_API.web.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorDto {
    @NotNull(message = "The parameter 'name' is required")
    @Size(max = 255)
    private String name;
    @NotNull(message = "The parameter 'specialty' is required")
    @Size(max = 100)
    private String specialty;
    @NotNull(message = "The parameter 'phone' is required")
    @Size(max = 50)
    private String phone;
}
