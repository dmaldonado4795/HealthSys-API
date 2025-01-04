package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.MedicalHistoryEntity;

import java.util.List;
import java.util.Optional;

/**
 * IMedicalHistoryService provides a contract for managing medical history records.
 * This interface defines the methods required for CRUD operations on
 * medical history entities in the HealthSys API.
 */
public interface IMedicalHistoryService {

    /**
     * Retrieves a list of all medical history records.
     *
     * @return a {@link List} of {@link MedicalHistoryEntity} objects representing all medical histories.
     */
    List<MedicalHistoryEntity> findAll();

    /**
     * Retrieves a medical history record by its unique identifier.
     *
     * @param id the unique identifier of the medical history record.
     * @return an {@link Optional} containing the {@link MedicalHistoryEntity} if found,
     *         or an empty {@link Optional} if no record is found with the given ID.
     */
    Optional<MedicalHistoryEntity> findById(long id);

    /**
     * Saves a new medical history record to the database.
     *
     * @param entity the {@link MedicalHistoryEntity} to save.
     * @return the saved {@link MedicalHistoryEntity} including any generated identifiers.
     */
    MedicalHistoryEntity save(MedicalHistoryEntity entity);

    /**
     * Updates an existing medical history record in the database.
     *
     * Note: The entity should already exist; otherwise, the behavior is undefined.
     *
     * @param entity the {@link MedicalHistoryEntity} containing updated data.
     * @return the updated {@link MedicalHistoryEntity}.
     */
    MedicalHistoryEntity update(MedicalHistoryEntity entity);
}
