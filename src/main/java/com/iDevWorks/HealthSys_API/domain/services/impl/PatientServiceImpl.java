package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.domain.services.IPatientService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
