package com.basic.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @파일명 : CheckPermissions.java
 * @설명 : 메서드 수준 권한 검사 어노테이션(RBAC)
 * @작성자 : 김승연
 * @작성일 : 2025.12.14
 * @변경이력 :
 *       2025.12.14 김승연 최초 생성
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermissions {
    String[] value();
}