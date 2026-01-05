package com.basic.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.basic.app.auth.CustomAccessDeniedHandler;
import com.basic.app.auth.CustomAuthenticationEntryPoint;
import com.basic.app.auth.CustomOAuth2SuccessHandler;
import com.basic.app.auth.CustomUserDetailsService;
import com.basic.app.jwt.JwtAuthorizationFilter;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

/**
 * @파일명 : SecurityConfig.java
 * @설명 : Spring Security 설정 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Profile("!test")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Autowired
  private JwtProperties jwtProperties;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

  @Autowired
  private StringRedisTemplate redisTemplate;

  /**
   * @기능 : SecurityFilterChain 설정 (WebSecurityConfigurerAdapter 대체)
   * @param http                  HttpSecurity 객체
   * @param authenticationManager 인증 매니저
   * @return SecurityFilterChain 보안 필터 체인 객체
   * @throws Exception 설정 중 발생할 수 있는 예외
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
      throws Exception {

    JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(
        authenticationManager,
        jwtProvider,
        jwtProperties,
        customUserDetailsService,
        redisTemplate);

    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configure(http)) // CORS 설정 활성화
        .formLogin(AbstractHttpConfigurer::disable) // Rest API 사용으로 폼 로그인 비활성화
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT 사용으로 세션사용안함

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 커스텀 예외 처리 핸들러 설정
            .accessDeniedHandler(new CustomAccessDeniedHandler())) // 인가 커스텀 예외 처리 핸들러 설정

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**", "/oauth2/**", "/login/oauth2/code/**")
            .permitAll() // 인증관련 로직은 인증 없이 접근 허용
            .requestMatchers("/admin/**")
            .hasAnyRole("MANAGER", "ADMIN") // 관리자 (시스템 관리자)
            .requestMatchers("/manager/**")
            .hasAnyRole("MANAGER", "ADMIN") // 매니저(서비스제공자 - 관리자)
            .requestMatchers("/host/**")
            .hasAnyRole("HOST", "MANAGER", "ADMIN") // 호스트(서비스제공자 - 일반)
            .requestMatchers("/user/**")
            .hasAnyRole("GUEST", "HOST", "MANAGER", "ADMIN") // 게스트(서비스 이용자)
            .anyRequest()
            .permitAll())
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .oauth2Login(oauth2 -> oauth2
            /* OAuth2 인증 시작 URL: /oauth2/authorization/{provider} */
            .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorization"))
            /* OAuth2 인증 완료시 redirect URL(각 Provider 사이트에서 이 패턴으로 등록) */
            .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
            /* 인증 및 인가 성공 후 처리되는 handler */
            .successHandler(customOAuth2SuccessHandler));
    return http.build();
  }

  /**
   * @기능 : 비밀번호 암호화에 사용할 인코더 Bean 등록 (로그인시 자동으로 비교)
   * @return BCryptPasswordEncoder 비밀번호 암호화 인코더 객체
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * @기능 : AuthenticationManager 등록
   * @param authenticationConfiguration 인증 설정 객체
   * @return AuthenticationManager 인증 매니저 객체
   * @throws Exception 설정 중 발생할 수 있는 예외
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}