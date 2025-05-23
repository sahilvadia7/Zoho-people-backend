package com.zoho.organization.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myConfig(){

        return new OpenAPI()
                .info(
                        new Info().title("Organization Service")
                                .description("Zoho People ~ Backend Project")
                );
//                .servers(List.of(new Server().url("http://localhost:8080").description("User Service")))
//                .tags(
//                        List.of(
//                                new Tag().name("User-Api")
//                        )
//                );
    }

}
