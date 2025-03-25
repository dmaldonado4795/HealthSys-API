package com.iDevWorks.HealthSys_API.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @NotNull(message = "The parameter 'refreshToken' is required")
    private String refreshToken;
}
