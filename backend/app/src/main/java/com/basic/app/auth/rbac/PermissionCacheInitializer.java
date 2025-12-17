package com.basic.app.auth.rbac;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.basic.app.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;

/**
 * @파일명 : PermissionCacheInitializer.java
 * @설명 : 애플리케이션 시작 시 권한 캐시 초기화 클래스
 * @EventListener(ApplicationReadyEvent.class)를 사용하여 애플리케이션이 완전히 시작된 후
 *                                              권한 캐시를 로드하는 역할을 수행
 * @작성자 : 김승연
 * @작성일 : 2025.12.16
 * @변경이력 :
 *       2025.12.16 김승연 최초 생성
 */

@Component
@ConditionalOnProperty(name = "app.security.permission-cache.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class PermissionCacheInitializer {

  private final RoleService roleService;

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    roleService.reloadPermissionsCache();
  }
}