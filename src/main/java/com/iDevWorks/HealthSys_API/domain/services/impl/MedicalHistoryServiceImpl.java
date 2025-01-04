package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.MedicalHistoryEntity;
import com.iDevWorks.HealthSys_API.domain.services.IMedicalHistoryService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.MedicalHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicalHistoryServiceImpl implements IMedicalHistoryService {
    private final MedicalHistoryRepository repository;

    public MedicalHistoryServiceImpl(MedicalHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MedicalHistoryEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MedicalHistoryEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public MedicalHistoryEntity save(MedicalHistoryEntity entity) {
        return repository.save(entity);
    }

    @Override
    public MedicalHistoryEntity update(MedicalHistoryEntity entity) {
        return repository.findById(entity.getHistoryId()).map(resp -> {
            if (!Objects.equals(resp.getDescription(), entity.getDescription()))
                resp.setDescription(entity.getDescription());
            if (!Objects.equals(resp.getPatient(), entity.getPatient()))
                resp.setPatient(entity.getPatient());
            if (!Objects.equals(resp.getDoctor(), entity.getDoctor()))
                resp.setDoctor(entity.getDoctor());
            return repository.save(resp);
        }).orElseThrow(() -> new EntityNotFoundException("Medical history not found with id"));
    }
}
