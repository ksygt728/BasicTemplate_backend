package com.basic.app.aop;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.basic.app.annotation.CheckPermissions;
import com.basic.app.auth.CustomUserDetails;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.RbacAccessDeniedException;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : PermissionCheckAspect.java
 * @설명 : 권한 체크 AOP 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.12.14
 * @변경이력 :
 *       2025.12.14 김승연 최초 생성
 */

@Aspect
@Component
@Log4j2
@Order(1)
public class PermissionCheckAspect {

  @Around("@annotation(checkPermissions)")
  public Object check(
      ProceedingJoinPoint joinPoint,
      CheckPermissions checkPermissions) throws Throwable {

    log.info("===[AOP] PermissionsCheckAspect 접근===");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

    List<String> userPermissions = userDetails.getPermissions();

    log.info("사용자 권한 목록: " + userPermissions);
    boolean allowed = Arrays.stream(checkPermissions.value())
        .anyMatch(userPermissions::contains);

    if (!allowed) {
      throw new RbacAccessDeniedException(ErrorCode.RBAC_ACCESS_DENIED);
    }

    return joinPoint.proceed();
  }
}