package uz.tridev.digital_library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Digital Library API",
                version = "v1",
                description = "Digital Library API for managing books and users",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Authorization header using Bearer scheme. Example: 'Authorization: Bearer <your_token>'"
)
public class OpenApiConfig {
}
