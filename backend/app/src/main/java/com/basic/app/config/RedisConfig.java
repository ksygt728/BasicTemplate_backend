package com.basic.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @파일명 : RedisConfig.java
 * @설명 : Redis 설정 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Configuration
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Value("${spring.data.redis.password:}")
  private String redisPassword;

  /**
   * @기능 : Redis 연결 팩토리 설정
   * @return RedisConnectionFactory Redis 연결 팩토리 객체
   */
  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
    return new LettuceConnectionFactory(config);
  }

  /**
   * @기능 : RedisTemplate 사용을 위한 String 타입 Redis Template 설정
   * @return StringRedisTemplate String 타입 Redis Template 객체
   */
  @Bean
  public StringRedisTemplate redisTemplate() {
    StringRedisTemplate redisTemplate = new StringRedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory());

    return redisTemplate;
  }
}