package com.github.donnyk22.project.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class Swagger {
    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Bookstore")
                        .description("Simple CRUD Bookstore API with AUTH")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                        .externalDocs(new ExternalDocumentation()
                        .description("Author Profile")
                        .url("https://www.linkedin.com/in/donnyk22/"));
    }
}