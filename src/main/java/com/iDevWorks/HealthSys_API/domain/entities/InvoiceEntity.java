package com.iDevWorks.HealthSys_API.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "MedicalHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long invoiceId;
    private Double totalAmount;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "patientId")
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn(name = "appointmentId")
    private AppointmentEntity appointment;
}
