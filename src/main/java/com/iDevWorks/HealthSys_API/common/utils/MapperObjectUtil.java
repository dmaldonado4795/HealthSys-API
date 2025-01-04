package com.iDevWorks.HealthSys_API.common.utils;

import com.iDevWorks.HealthSys_API.domain.entities.*;
import com.iDevWorks.HealthSys_API.web.dtos.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class for mapping DTO objects to Entity objects.
 * Provides static methods to transform incoming data transfer objects (DTOs) into their corresponding domain entities.
 * <p>
 * This helps centralize the mapping logic and ensures consistency throughout the application.
 */
public final class MapperObjectUtil {

    /**
     * Maps a {@link UserDto} to a {@link UserEntity}.
     *
     * @param id  the ID of the user.
     * @param dto the {@link UserDto} containing user data.
     * @return a {@link UserEntity} representing the user.
     */
    public static UserEntity toUserEntity(long id, UserDto dto) {
        return new UserEntity(
                id,
                dto.getUsername(),
                dto.getPassword(),
                dto.isActive()
        );
    }

    /**
     * Maps a {@link PatientDto} to a {@link PatientEntity}.
     *
     * @param id  the ID of the patient.
     * @param dto the {@link PatientDto} containing patient data.
     * @return a {@link PatientEntity} representing the patient.
     */
    public static PatientEntity toPatientEntity(long id, PatientDto dto) {
        return new PatientEntity(
                id,
                dto.getName(),
                dto.getDateOfBirth(),
                dto.getGender(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getEmail()
        );
    }

    /**
     * Maps a {@link DoctorDto} to a {@link DoctorEntity}.
     *
     * @param id  the ID of the doctor.
     * @param dto the {@link DoctorDto} containing doctor data.
     * @return a {@link DoctorEntity} representing the doctor.
     */
    public static DoctorEntity toDoctorEntity(long id, DoctorDto dto) {
        return new DoctorEntity(
                id,
                dto.getName(),
                dto.getSpecialty(),
                dto.getPhone()
        );
    }

    /**
     * Maps an {@link AppointmentDto} to an {@link AppointmentEntity}.
     *
     * @param id  the ID of the appointment.
     * @param dto the {@link AppointmentDto} containing appointment data.
     * @return an {@link AppointmentEntity} representing the appointment.
     */
    public static AppointmentEntity toAppointment(long id, AppointmentDto dto) {
        return new AppointmentEntity(
                id,
                dto.getDate(),
                dto.getReason(),
                dto.getPatient(),
                dto.getDoctor()
        );
    }

    /**
     * Maps a {@link MedicalHistoryDto} to a {@link MedicalHistoryEntity}.
     *
     * @param id  the ID of the medical history record.
     * @param dto the {@link MedicalHistoryDto} containing medical history data.
     * @return a {@link MedicalHistoryEntity} representing the medical history record.
     */
    public static MedicalHistoryEntity toMedicalHistory(long id, MedicalHistoryDto dto) {
        return new MedicalHistoryEntity(
                id,
                dto.getDescription(),
                dto.getPatient(),
                dto.getDoctor(),
                LocalDateTime.now()
        );
    }

    /**
     * Maps an {@link InvoiceDto} to an {@link InvoiceEntity}.
     *
     * @param id  the ID of the invoice.
     * @param dto the {@link InvoiceDto} containing invoice data.
     * @return an {@link InvoiceEntity} representing the invoice.
     */
    public static InvoiceEntity toInvoice(long id, InvoiceDto dto) {
        return new InvoiceEntity(
                id,
                dto.getTotalAmount(),
                LocalDate.now(),
                dto.getAppointment()
        );
    }
}
