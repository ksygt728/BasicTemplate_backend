package com.basic.app.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class AuditorAwareConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> {
      // 실제 로그인 정보에서 가져오기 (예: Spring Security 사용 시)
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !authentication.isAuthenticated()) {
        return Optional.of("SYSTEM");
      }
      return Optional.of(authentication.getName());
    };
  }
}