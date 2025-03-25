package com.iDevWorks.HealthSys_API.infrastructure.repositories;

import com.iDevWorks.HealthSys_API.domain.entities.RefreshTokenEntity;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    int deleteByUser(UserEntity user);
}
