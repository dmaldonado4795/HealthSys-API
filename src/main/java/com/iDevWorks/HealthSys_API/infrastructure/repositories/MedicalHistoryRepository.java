package com.iDevWorks.HealthSys_API.infrastructure.repositories;

import com.iDevWorks.HealthSys_API.domain.entities.MedicalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {
}
