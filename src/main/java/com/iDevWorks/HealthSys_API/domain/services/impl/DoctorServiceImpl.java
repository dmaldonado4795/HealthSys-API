package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import com.iDevWorks.HealthSys_API.domain.services.IDoctorService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements IDoctorService {
    @Autowired
    private DoctorRepository repository;

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
        return null;
    }
}
