package com.basic.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @파일명 : NoAopLogging.java
 * @설명 : AOP 로깅 제외 어노테이션
 * @작성자 : 김승연
 * @작성일 : 2025.12.16
 * @변경이력 :
 *       2025.12.16 김승연 최초 생성
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAopLogging {
}