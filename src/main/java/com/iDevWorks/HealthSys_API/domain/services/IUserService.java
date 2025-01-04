package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserEntity> findAll();

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity entity);

    UserEntity update(UserEntity entity);
}
