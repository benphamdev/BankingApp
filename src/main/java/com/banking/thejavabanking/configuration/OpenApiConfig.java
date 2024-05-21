package com.banking.thejavabanking.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@OpenAPIDefinition(
//        info = @io.swagger.v3.oas.annotations.info.Info(
//                title = "The Java Banking API",
//                version = "1.0",
//                description = "The Java Banking API",
//                contact = @Contact(
//                        name = "Chien",
//                        email = "22162006@student.hcmute.edu.vn",
//                        url = "https://github.com/phamduyben/MobileWin11"
//                ),
//                license = @License(
//                        name = "Banking API License",
//                        url = "https://github.com/phamduyben/MobileWin11"
//                )
//        ),
//        externalDocs = @ExternalDocumentation(
//                description = "The Java Banking API Wiki",
//                url = "https://github.com/phamduyben/MobileWin11"
//        )
//)
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(
            @Value("${open.api.title}") String title,
            @Value("${open.api.version}") String version,
            @Value("${open.api.description}") String description,
            @Value("${open.api.license}") String license,
            @Value("${open.api.licenseUrl}") String licenseUrl,
            @Value("${open.api.serverUrl}") String serverUrl,
            @Value("${open.api.serverDescription}") String serverDescription
    ) {
        return new OpenAPI()
                .info(
                        new Info().title(title)
                                  .version(version)
                                  .description(description)
                                  .license(new License().name(license)
                                                        .url(licenseUrl))
                )
                .servers(
                        List.of(
                                new Server().url(serverUrl)
                                            .description(serverDescription),
                                new Server().url("https://thejavabanking.herokuapp.com")
                                            .description("Production server")
                        )
                );
//                .components(new Components()
//                                    .addSecuritySchemes(
//                                            "bearerAuth",
//                                            new SecurityScheme()
//                                                    .type(SecurityScheme.Type.HTTP)
//                                                    .scheme("bearer")
//                                                    .bearerFormat("JWT")
//                                    ))
//                .security(List.of(new SecurityRequirement().addList("bearerAuth")));

    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                             .group("api-service-1")
                             .packagesToScan("com.banking.thejavabanking")
                             .build();
    }
}
