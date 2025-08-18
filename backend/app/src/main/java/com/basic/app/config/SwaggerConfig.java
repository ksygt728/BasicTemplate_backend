package com.basic.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Value("${jwt.access-token-header}")
    private String headerName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API 문서").description("Backend API documentation for CBSK project")
                        .version("v1.0.0"))

                // 모든 엔드포인트에 적용할 Security Requirement
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                // 실제 Security Scheme 정의
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY) // HTTP Bearer가 아닌 APIKEY 타입 사용
                                        .in(SecurityScheme.In.HEADER) // 헤더로 전달
                                        .name(headerName) // 헤더 이름 지정
                        ));
    }

}