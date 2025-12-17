package com.example.crud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for API documentation
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CRUD Application API",
                version = "1.0.0",
                description = "Spring Boot CRUD Operations with SQL Database API Documentation",
                contact = @Contact(
                        name = "CRUD Application",
                        url = "https://example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Development Server"
                ),
                @Server(
                        url = "http://localhost:8080/api",
                        description = "Development Server with API Context"
                )
        }
)
public class OpenApiConfig {
}
