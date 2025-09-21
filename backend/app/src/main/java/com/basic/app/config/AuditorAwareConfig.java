package com.basic.app.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : AuditorAwareConfig.java
 * @설명 : JPA Auditing 설정 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@EnableJpaAuditing
@Configuration
@Log4j2
public class AuditorAwareConfig {

  /**
   * @기능 : JPA Auditing을 위한 현재 사용자 정보 제공
   * @return AuditorAware<String> 현재 사용자 정보를 제공하는 AuditorAware 객체
   */
  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> {
      // 실제 로그인 정보에서 가져오기 (예: Spring Security 사용 시)
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !authentication.isAuthenticated()
          || authentication.getName().equals("anonymousUser")) {
        return Optional.of("SYSTEM");
      }
      return Optional.of(authentication.getName());
    };
  }
}