package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;
import com.iDevWorks.HealthSys_API.domain.services.IAppointmentService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepository repository;

    public AppointmentServiceImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AppointmentEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AppointmentEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public AppointmentEntity save(AppointmentEntity entity) {
        return repository.save(entity);
    }

    @Override
    public AppointmentEntity update(AppointmentEntity entity) {
        return repository.findById(entity.getAppointmentId()).map(resp -> {
            if (!Objects.equals(resp.getDate(), entity.getDate()))
                resp.setDate(entity.getDate());
            if (!Objects.equals(resp.getReason(), entity.getReason()))
                resp.setReason(entity.getReason());
            if (!Objects.equals(resp.getPatient(), entity.getPatient()))
                resp.setPatient(entity.getPatient());
            if (!Objects.equals(resp.getDoctor(), entity.getDoctor()))
                resp.setDoctor(entity.getDoctor());
            return repository.save(entity);
        }).orElseThrow(() -> new EntityNotFoundException("Appointment not found with id"));
    }
}
