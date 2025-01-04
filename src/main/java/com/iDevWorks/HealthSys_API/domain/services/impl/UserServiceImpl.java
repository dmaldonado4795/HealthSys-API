package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import com.iDevWorks.HealthSys_API.domain.services.IUserService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        repository.findByUsername(entity.getUsername()).ifPresent(user -> {
            throw new EntityNotFoundException(String.format("User already exists with username: %s", entity.getUsername()));
        });
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return repository.findById(entity.getUserId()).map(resp -> {
            if (!Objects.equals(resp.getUsername(), entity.getUsername())) {
                resp.setUsername(entity.getUsername());
            }

            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            if (!passwordEncoder.matches(resp.getPassword(), entity.getPassword())) {
                resp.setPassword(entity.getPassword());
            }

            if (!Objects.equals(resp.isActive(), entity.isActive())) {
                resp.setActive(entity.isActive());
            }

            return repository.save(entity);
        }).orElseThrow(() -> new EntityNotFoundException("User not found with id"));
    }
}
