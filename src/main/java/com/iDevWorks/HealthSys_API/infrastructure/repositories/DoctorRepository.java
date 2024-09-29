package com.iDevWorks.HealthSys_API.infrastructure.repositories;

import com.iDevWorks.HealthSys_API.domain.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
