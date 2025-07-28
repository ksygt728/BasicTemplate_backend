package com.basic.app.config;

// config/AuditorAwareImpl.java

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    return Optional.of("SYSTEM_Audit"); // 기본적으로 "SYSTEM"을 반환합니다.

    // throw new UnsupportedOperationException("Unimplemented method
    // 'getCurrentAuditor'");
  }

  // Spring Security를 사용할떄 주석을 해제하고 사용하세요.
  // @Override
  // public Optional<String> getCurrentAuditor() {
  // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  // if (auth == null || !auth.isAuthenticated()) {
  // return Optional.of("SYSTEM"); // 또는 Optional.empty()
  // }
  // return Optional.of(auth.getName());
  // }
}