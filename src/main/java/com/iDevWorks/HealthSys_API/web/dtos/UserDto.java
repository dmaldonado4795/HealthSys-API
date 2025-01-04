package com.iDevWorks.HealthSys_API.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotNull(message = "The parameter 'username' is required")
    private String username;
    @NotNull(message = "The parameter 'password' is required")
    private String password;
    @NotNull(message = "The parameter 'active' is required")
    private boolean active;
}
