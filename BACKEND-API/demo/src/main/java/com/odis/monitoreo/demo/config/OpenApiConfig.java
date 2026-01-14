package com.odis.monitoreo.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Monitoreo Backend API")
                        .version("1.0")
                        .description("Documentación de la API para el sistema de monitoreo ODIS")
                        .termsOfService("Términos de servicio")
                        .contact(new Contact()
                                .name("ODIS")
                                .email("sistemas@odis.mx")
                                .url("https://odis.mx")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor Local"),
                        new Server().url("https://monitor.odis.com.mx").description("Servidor de Producción")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)));
    }
}
