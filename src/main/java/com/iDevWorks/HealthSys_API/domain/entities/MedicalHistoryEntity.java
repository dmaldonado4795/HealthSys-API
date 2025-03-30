package com.iDevWorks.HealthSys_API.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Medical_History")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long historyId;
    private String description;
    @ManyToOne
    @JoinColumn(name = "patientId")
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private DoctorEntity doctor;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
