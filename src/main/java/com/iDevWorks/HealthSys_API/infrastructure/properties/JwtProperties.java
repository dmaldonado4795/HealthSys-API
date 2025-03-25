package com.iDevWorks.HealthSys_API.infrastructure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private int expiration;
    private String issuer;
    private int refreshTokenExpiration;
}
