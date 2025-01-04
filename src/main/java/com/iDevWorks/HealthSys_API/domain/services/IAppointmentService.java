package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;

import java.util.List;
import java.util.Optional;

/**
 * IAppointmentService provides a contract for managing appointment records.
 * This interface defines the methods required for CRUD operations on
 * appointment entities in the HealthSys API.
 */
public interface IAppointmentService {

    /**
     * Retrieves a list of all appointments.
     *
     * @return a {@link List} of {@link AppointmentEntity} objects representing all appointments.
     */
    List<AppointmentEntity> findAll();

    /**
     * Retrieves an appointment by its unique identifier.
     *
     * @param id the unique identifier of the appointment.
     * @return an {@link Optional} containing the {@link AppointmentEntity} if found,
     *         or an empty {@link Optional} if no appointment is found with the given ID.
     */
    Optional<AppointmentEntity> findById(long id);

    /**
     * Saves a new appointment record to the database.
     *
     * @param entity the {@link AppointmentEntity} to save.
     * @return the saved {@link AppointmentEntity} including any generated identifiers.
     */
    AppointmentEntity save(AppointmentEntity entity);

    /**
     * Updates an existing appointment record in the database.
     *
     * Note: The entity should already exist; otherwise, the behavior is undefined.
     *
     * @param entity the {@link AppointmentEntity} containing updated data.
     * @return the updated {@link AppointmentEntity}.
     */
    AppointmentEntity update(AppointmentEntity entity);
}
