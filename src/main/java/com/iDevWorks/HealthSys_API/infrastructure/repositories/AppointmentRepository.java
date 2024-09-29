package com.iDevWorks.HealthSys_API.infrastructure.repositories;

import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
