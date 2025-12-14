package com.basic.app.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * @파일명 : JwtProperties.java
 * @설명 : JWT 설정 정보를 담는 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 *       2025.12.14 김승연 RBAC방식의 권한 체크로 인한 리팩토링
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private String accessTokenHeader;
  private String refreshTokenHeader;
  private String bearerType;
  private String issuer;
  private String secretKey;
  private Subject subject;
  private ExpireTime expireTime;
  private Duration deadTimeForServer;
  private String testId;
  private String env;

  @Getter
  @Setter
  public static class Subject {
    private String first;
    private String second;
    private String third;
    private String fourth;
    private String fifth;
  }

  @Getter
  @Setter
  public static class ExpireTime {
    private Duration accessToken;
    private Duration refreshToken;
  }
}