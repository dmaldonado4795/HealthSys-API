package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.RefreshTokenEntity;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import com.iDevWorks.HealthSys_API.domain.services.IRefreshTokenService;
import com.iDevWorks.HealthSys_API.infrastructure.properties.JwtProperties;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(
            JwtProperties jwtProperties,
            RefreshTokenRepository refreshTokenRepository) {
        this.jwtProperties = jwtProperties;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshTokenEntity createRefresthToken(UserEntity entity) {
        RefreshTokenEntity tokenEntity = new RefreshTokenEntity();
        tokenEntity.setUser(entity);
        tokenEntity.setToken(UUID.randomUUID().toString());
        tokenEntity.setExpiryDate(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiration()));

        return refreshTokenRepository.save(tokenEntity);
    }

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(RefreshTokenEntity entity) {
        return entity.getExpiryDate().isBefore(Instant.now());
    }

    @Override
    public void deleteByUserId(long id) {
        refreshTokenRepository.deleteById(id);
    }
}
