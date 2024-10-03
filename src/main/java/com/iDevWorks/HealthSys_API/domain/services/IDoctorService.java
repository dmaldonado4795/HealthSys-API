package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;

import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    List<DoctorEntity> findAll();

    Optional<DoctorEntity> findById(long id);

    DoctorEntity save(DoctorEntity entity);
}
