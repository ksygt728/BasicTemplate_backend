package com.basic.app.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

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
  }

  @Getter
  @Setter
  public static class ExpireTime {
    private Duration accessToken;
    private Duration refreshToken;
  }
}