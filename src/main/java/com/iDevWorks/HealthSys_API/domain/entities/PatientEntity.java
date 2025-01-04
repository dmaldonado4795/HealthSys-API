package com.iDevWorks.HealthSys_API.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long patientId;
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String phone;
    private String email;
}
