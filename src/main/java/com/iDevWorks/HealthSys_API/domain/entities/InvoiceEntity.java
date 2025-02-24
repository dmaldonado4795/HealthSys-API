package com.iDevWorks.HealthSys_API.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long invoiceId;
    @Column(name = "total_amount")
    private Double totalAmount;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "appointmentId")
    private AppointmentEntity appointment;
}
