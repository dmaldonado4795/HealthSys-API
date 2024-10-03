package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    List<PatientEntity> findAll();

    Optional<PatientEntity> findById(long id);

    PatientEntity save(PatientEntity entity);

    PatientEntity update(PatientEntity entity);

    PatientEntity patch(PatientEntity entity);
}
