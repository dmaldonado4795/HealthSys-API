package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.RefreshTokenEntity;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;

import java.util.Optional;

public interface IRefreshTokenService {
    RefreshTokenEntity createRefresthToken(UserEntity entity);

    Optional<RefreshTokenEntity> findByToken(String token);

    boolean isExpired(RefreshTokenEntity entity);

    void deleteByUserId(long id);
}
