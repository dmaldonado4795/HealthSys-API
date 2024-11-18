package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;

import java.util.List;
import java.util.Optional;

/**
 * IPatientService provides a contract for managing patient data.
 * This interface defines the methods required for CRUD operations
 * on patient entities in the HealthSys API.
 */
public interface IPatientService {

    /**
     * Retrieves a list of all patients.
     *
     * @return a {@link List} of {@link PatientEntity} objects representing all patients.
     */
    List<PatientEntity> findAll();

    /**
     * Retrieves a patient by their unique identifier.
     *
     * @param id the unique identifier of the patient.
     * @return an {@link Optional} containing the {@link PatientEntity} if found,
     *         or an empty {@link Optional} if no patient is found with the given ID.
     */
    Optional<PatientEntity> findById(long id);

    /**
     * Saves a new patient entity to the database.
     *
     * @param entity the {@link PatientEntity} to save.
     * @return the saved {@link PatientEntity} including any generated identifiers.
     */
    PatientEntity save(PatientEntity entity);

    /**
     * Updates an existing patient entity in the database.
     *
     * Note: The entity should already exist; otherwise, the behavior is undefined.
     *
     * @param entity the {@link PatientEntity} containing updated data.
     * @return the updated {@link PatientEntity}.
     */
    PatientEntity update(PatientEntity entity);
}
