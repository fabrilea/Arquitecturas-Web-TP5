package edu.tudai.microserviciofactura.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Microservicio de Monopatines")
                        .version("1.0")
                        .description("Documentación de API para el microservicio de administración de monopatines."));
    }
}
