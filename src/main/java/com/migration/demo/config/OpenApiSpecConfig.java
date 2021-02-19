package com.migration.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiSpecConfig {

    @Value("${app.title}")
    private String title;

    @Value("${app.describe}")
    private String describe;

    @Value("${app.contact.name}")
    private String name;

    @Value("${app.contact.email}")
    private String email;

    @Value("${app.contact.url}")
    private String url;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(title)
                        .description(describe)
                        .contact(new Contact()
                                .name(name)
                                .email(email)
                                .url(url)));
    }

}
