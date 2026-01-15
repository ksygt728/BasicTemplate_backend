package com.basic.app.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.basic.app.api.ResponseApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @파일명 : SwaggerConfig.java
 * @설명 : Swagger OpenAPI 설정 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Configuration
public class SwaggerConfig {

        @Value("${jwt.access-token-header}")
        private String headerName;

        /**
         * @기능 : 커스텀 OpenAPI 설정 생성
         * @return OpenAPI Swagger 문서 설정 객체
         */
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("API 문서")
                                                .description("Backend API documentation for CBMS project")
                                                .version("v1.0.0"))

                                // 모든 엔드포인트에 적용할 Security Requirement
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                                // 실제 Security Scheme 정의
                                .components(new io.swagger.v3.oas.models.Components()
                                                .addSecuritySchemes("bearerAuth",
                                                                new SecurityScheme()
                                                                                /* Bareer타입이 아닌 API Key 타입 */
                                                                                .type(SecurityScheme.Type.APIKEY)
                                                                                .in(SecurityScheme.In.HEADER) // 헤더로 전달
                                                                                .name(headerName) // 헤더 이름 지정
                                                ));

        }

}