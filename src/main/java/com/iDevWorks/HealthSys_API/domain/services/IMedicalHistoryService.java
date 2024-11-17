package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.MedicalHistoryEntity;

import java.util.List;
import java.util.Optional;

public interface IMedicalHistoryService {
    List<MedicalHistoryEntity> findAll();

    Optional<MedicalHistoryEntity> findById(long id);

    MedicalHistoryEntity save(MedicalHistoryEntity entity);

    MedicalHistoryEntity update(MedicalHistoryEntity entity);
}
