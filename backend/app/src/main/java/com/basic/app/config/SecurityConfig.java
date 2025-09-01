package com.basic.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  // private final JwtProvider jwtProvider;
  private final CustomUserDetailsService userDetailsService;

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
   * SecurityFilterChain 설정 (WebSecurityConfigurerAdapter 대체)
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
        .formLogin(AbstractHttpConfigurer::disable) // Rest API 사용으로 폼 로그인 비활성화
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT 사용으로 세션사용안함

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 커스텀 예외 처리 핸들러 설정
            .accessDeniedHandler(new CustomAccessDeniedHandler())) // 인가 커스텀 예외 처리 핸들러 설정

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**", "/login/oauth2/code/**")
            .permitAll() // 인증관련 로직은 인증 없이 접근 허용
            .requestMatchers("/admin/**")
            .hasRole("ADMIN") // 관리자 (시스템 관리자)
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
            /* OAuth2 인증 시작 URL뒤에 /{provider} 추가햐여 사용 */
            .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/auth/oauth2"))
            /*
             * OAuth2 인증 완료시 redirect URL(security에서 자동으로 사용함 - 각 Provider의 사이트에서 이 패턴으로
             * 등록해야함)
             */
            .redirectionEndpoint(endpoint -> endpoint.baseUri("/login/oauth2/code/*"))
            /* 인증 및 인가 성공 후 처리되는 handler */
            .successHandler(customOAuth2SuccessHandler));
    return http.build();
  }

  /**
   * 비밀번호 암호화에 사용할 인코더 Bean 등록(로그인시 자동으로 비교)
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * AuthenticationManager 등록
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}