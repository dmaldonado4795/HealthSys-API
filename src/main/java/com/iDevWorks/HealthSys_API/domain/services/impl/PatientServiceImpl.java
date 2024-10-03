package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {
    @Autowired
    private PatientRepository repository;

    @Override
    public List<PatientEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PatientEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public PatientEntity save(PatientEntity entity) {
        return repository.save(entity);
    }

    @Override
    public PatientEntity update(PatientEntity entity) {
        return repository.findById(entity.getPatientId()).map(resp -> {
            resp.setName(entity.getName());
            resp.setDateOfBirth(entity.getDateOfBirth());
            resp.setGender(entity.getGender());
            resp.setAddress(entity.getAddress());
            resp.setPhone(entity.getPhone());
            resp.setEmail(entity.getEmail());
            return repository.save(resp);
        }).orElseThrow(() -> new EntityNotFoundException("Patient not found with id"));
    }

    @Override
    public PatientEntity patch(PatientEntity entity) {
        return repository.findById(entity.getPatientId()).map(resp -> {
            if (!Objects.equals(resp.getName(), entity.getName()))
                resp.setName(entity.getName());
            if (!Objects.equals(resp.getDateOfBirth(), entity.getDateOfBirth()))
                resp.setDateOfBirth(entity.getDateOfBirth());
            if (!Objects.equals(resp.getGender(), entity.getGender()))
                resp.setGender(entity.getGender());
            if (!Objects.equals(resp.getAddress(), entity.getAddress()))
                resp.setAddress(entity.getAddress());
            if (!Objects.equals(resp.getPhone(), entity.getPhone()))
                resp.setPhone(entity.getPhone());
            if (!Objects.equals(resp.getEmail(), entity.getEmail()))
                resp.setEmail(entity.getEmail());
            return repository.save(resp);
        }).orElseThrow(() -> new EntityNotFoundException("Patient not found with id"));
    }
}
