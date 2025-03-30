package com.iDevWorks.HealthSys_API.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import static com.iDevWorks.HealthSys_API.common.api.ApiPath.PATH_V1;
import static com.iDevWorks.HealthSys_API.common.schema.Schema.BEARER_SCHEMA;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation with security schemes and basic info
     * @return Configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthSys-API")
                        .version("v1")
                        .description("HealthSys API Documentation"))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEMA))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEMA,
                                new SecurityScheme()
                                        .name(BEARER_SCHEMA)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    /**
     * Creates a grouped API configuration for public endpoints
     * @return GroupedOpenApi instance configured for public endpoints
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch(PATH_V1 + "/**")
                .build();
    }
}
