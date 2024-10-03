package com.iDevWorks.HealthSys_API.web.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDTO {
    @NotNull(message = "The parameter 'name' is required")
    @Size(max = 255)
    private String name;
    @Column(name = "date_of_birth")
    @NotNull(message = "The parameter 'dateOfBirth' is required")
    private LocalDate dateOfBirth;
    @NotNull(message = "The parameter 'gender' is required")
    @Size(max = 10)
    private String gender;
    @NotNull(message = "The parameter 'address' is required")
    @Size(max = 255)
    private String address;
    @NotNull(message = "The parameter 'phone' is required")
    @Size(max = 50)
    private String phone;
    @Email
    @NotNull(message = "The parameter 'email' is required")
    @Size(max = 100)
    private String email;
}
