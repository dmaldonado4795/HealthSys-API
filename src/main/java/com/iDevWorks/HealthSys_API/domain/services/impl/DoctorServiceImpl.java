package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements IDoctorService {
    private final DoctorRepository repository;

    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DoctorEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DoctorEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public DoctorEntity save(DoctorEntity entity) {
        return repository.save(entity);
    }

    @Override
    public DoctorEntity update(DoctorEntity entity) {
        return repository.findById(entity.getDoctorId()).map(resp -> {
            if (!Objects.equals(resp.getName(), entity.getName()))
                resp.setName(entity.getName());
            if (!Objects.equals(resp.getSpecialty(), entity.getSpecialty()))
                resp.setSpecialty(entity.getSpecialty());
            if (!Objects.equals(resp.getPhone(), entity.getPhone()))
                resp.setPhone(entity.getPhone());
            return repository.save(resp);
        }).orElseThrow(() -> new EntityNotFoundException("Doctor not found with id"));
    }
}
