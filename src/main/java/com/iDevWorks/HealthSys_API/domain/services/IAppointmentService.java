package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;

import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    List<AppointmentEntity> findAll();

    Optional<AppointmentEntity> findById(long id);

    AppointmentEntity save(AppointmentEntity entity);

    AppointmentEntity update(AppointmentEntity entity);
}
