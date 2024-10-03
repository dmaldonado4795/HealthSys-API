package com.iDevWorks.HealthSys_API.common.utils;

import com.iDevWorks.HealthSys_API.domain.entities.PatientEntity;
import com.iDevWorks.HealthSys_API.web.dtos.PatientRequestDTO;

public final class ObjectMappingUtil {
    public static PatientEntity toPatientEntity(long id, PatientRequestDTO dto) {
        return new PatientEntity(
                id,
                dto.getName(),
                dto.getDateOfBirth(),
                dto.getGender(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getEmail()
        );
    }
}
