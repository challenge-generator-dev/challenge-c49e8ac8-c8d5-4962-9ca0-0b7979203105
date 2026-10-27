package com.pragma.paymentservice.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Payment Service API")
                .version("1.0.0")
                .description("""
                    API REST para la gestión de solicitudes de pago.
                    
                    Este servicio proporciona funcionalidades para:
                    - Procesar solicitudes de pago con soporte de idempotencia
                    - Consultar el estado de operaciones de pago existentes
                    - Manejar múltiples canales de operación
                    
                    La idempotencia se garantiza mediante claves únicas compuestas por
                    número de operación y canal, asegurando que dos invocaciones con
                    la misma clave produzcan un solo registro.
                    """)
                .contact(new Contact()
                    .name("Equipo de Pagos")
                    .email("pagos@pragma.com")
                    .url("https://www.pragma.com"))
                .license(new License()
                    .name("Licencia Proprietaria")
                    .url("https://www.pragma.com/licencia")))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Token de autenticación JWT")));
    }
}