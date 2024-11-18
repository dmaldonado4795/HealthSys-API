package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;

import java.util.List;
import java.util.Optional;

/**
 * IDoctorService provides a contract for managing doctor records.
 * This interface defines the methods required for CRUD operations on
 * doctor entities in the HealthSys API.
 */
public interface IDoctorService {

    /**
     * Retrieves a list of all doctors.
     *
     * @return a {@link List} of {@link DoctorEntity} objects representing all doctors.
     */
    List<DoctorEntity> findAll();

    /**
     * Retrieves a doctor by their unique identifier.
     *
     * @param id the unique identifier of the doctor.
     * @return an {@link Optional} containing the {@link DoctorEntity} if found,
     *         or an empty {@link Optional} if no doctor is found with the given ID.
     */
    Optional<DoctorEntity> findById(long id);

    /**
     * Saves a new doctor record to the database.
     *
     * @param entity the {@link DoctorEntity} to save.
     * @return the saved {@link DoctorEntity} including any generated identifiers.
     */
    DoctorEntity save(DoctorEntity entity);

    /**
     * Updates an existing doctor record in the database.
     *
     * Note: The entity should already exist; otherwise, the behavior is undefined.
     *
     * @param entity the {@link DoctorEntity} containing updated data.
     * @return the updated {@link DoctorEntity}.
     */
    DoctorEntity update(DoctorEntity entity);
}
