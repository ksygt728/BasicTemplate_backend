package com.basic.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @파일명 : WebConfig.java
 * @설명 : CORS(Cross-Origin Resource Sharing) 설정
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 *       2025.10.06 김승연 CORS 설정 수정 (Spring Boot 3.5 호환)
 */
@Configuration
public class WebConfig {

  @Value("${front-end.url}")
  private String FRONT_END_URL;

  /**
   * @기능 : CORS 필터 설정
   * @return CorsFilter CORS 설정이 적용된 필터 객체
   */
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();

    // 자격 증명 허용 설정
    config.setAllowCredentials(true);

    // 허용할 원본 URL 설정 (localhost:3000에서 오는 요청 허용)
    config.addAllowedOriginPattern(FRONT_END_URL);
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    // 노출할 헤더 설정
    config.addExposedHeader("Authorization");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }
}