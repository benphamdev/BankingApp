package com.banking.thejavabanking;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "The Java Banking API",
                version = "1.0",
                description = "The Java Banking API",
                contact = @Contact(
                        name = "Chien",
                        email = "22162006@student.hcmute.edu.vn",
                        url = "https://github.com/phamduyben/MobileWin11"
                ),
                license = @License(
                        name = "Banking API License",
                        url = "https://github.com/phamduyben/MobileWin11"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "The Java Banking API Wiki",
                url = "https://github.com/phamduyben/MobileWin11"
        )
)
@EnableScheduling
public class TheJavaBankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TheJavaBankingApplication.class, args);
    }
}
