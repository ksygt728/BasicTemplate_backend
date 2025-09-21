package com.basic.app.jwt;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.basic.app.api.ResponseApi;
import com.basic.app.auth.CustomUserDetailsService;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.JwtExeption;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : JwtAuthorizationFilter.java
 * @설명 : JWT 인증 필터 클래스
 *     권한이나 인증이 필요한 특정주소를 요청했을때 BasicAuthenticationFilter를 탐.
 *     권한이나 인증이 필요하지 않은 요청은 타지 않음
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtProvider jwtProvider;
  private final JwtProperties jwtProperties;
  private final CustomUserDetailsService customUserDetailsService;
  private final StringRedisTemplate redisTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * @기능 : JwtAuthorizationFilter 생성자
   * @param authenticationManager    인증 매니저
   * @param jwtProvider              JWT 프로바이더
   * @param jwtProperties            JWT 설정
   * @param customUserDetailsService 사용자 상세 서비스
   * @param redisTemplate            Redis 템플릿
   */
  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
      JwtProperties jwtProperties,
      CustomUserDetailsService customUserDetailsService, StringRedisTemplate redisTemplate) {
    super(authenticationManager);
    this.jwtProvider = jwtProvider;
    this.jwtProperties = jwtProperties;
    this.customUserDetailsService = customUserDetailsService;
    this.redisTemplate = redisTemplate;
  }

  /**
   * @기능 : JWT 인증 필터 내부 처리
   * @param request  HTTP 요청
   * @param response HTTP 응답
   * @param chain    필터 체인
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info("[ACCESS] START JwtAthorizationFilter");

    /* 테스트 용 */
    boolean testToken = Boolean.parseBoolean(request.getHeader("test-token"));

    if (testToken && jwtProperties.getEnv().equals("dev")) {
      log.info("[TEST LOG] : 테스트용 토큰 사용 여부(환경 : {} 아이디 : {})", jwtProperties.getEnv(), jwtProperties.getTestId());
      Authentication authentication = jwtProvider.getAuthentication(jwtProperties.getTestId());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
      return;
    }

    // 1. Access Token을 들고 왔는지 확인
    String jwtAccessToken = jwtProvider.resolveAccessToken(request);
    String jwtRefreshToken = jwtProvider.resolveRefreshToken(request);

    // Case 1. Access Token, Refresh Token 둘 다 없는 경우, 일반 요청으로 인증 없이 통과
    if (jwtAccessToken == null && jwtRefreshToken == null) {
      log.warn("TEST LOG : Access Token과 Refresh Token이 모두 없습니다. 인증 없이 통과합니다.");
      chain.doFilter(request, response); // 인증 없이 통과
      return;
    }

    // Case 2. Access Token과 Refresh Token 둘 다 있는 경우, 잘못된 요청임으로 예외처리
    if (jwtAccessToken != null && jwtRefreshToken != null) {
      log.warn("[JWT INFO] : Access Token과 Refresh Token이 모두 있습니다. 잘못된 요청입니다.");
      try {
        showErrorLogFormat(response, new JwtExeption(null), ErrorCode.JWT_ISSUE_ATRT_EXSIT);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }

    // Case 3. Access Token을 들고온 경우 유효한지 확인
    try {

      if (jwtAccessToken != null) {
        log.warn("TEST LOG : Access Token을 들고온 경우 유효한지 확인");

        boolean isVerified = jwtProvider.isVaildAccessToken(jwtAccessToken);
        if (isVerified) {
          log.warn("TEST LOG : Access Token이 유효합니다. 통과");
          // 2-0. 블랙리시트에 저장된 Access Token인지 확인
          // 나중에 로그아웃이랑 같이 추가

          // 2-1. JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체생성
          String userId = jwtProvider.getClaimId(jwtAccessToken);
          Authentication authentication = jwtProvider.getAuthentication(userId);

          // 2-2. 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장(권한 처리)
          SecurityContextHolder.getContext().setAuthentication(authentication);

          // 2-3. 통과
          chain.doFilter(request, response);
          return;
        }
      }
    } catch (TokenExpiredException e) {
      try {
        log.info("[JWT INFO] Access Token이 만료되었습니다. RefreshToken으로 갱신해주세요.");
        showErrorLogFormat(response, e, ErrorCode.JWT_ISSUE_ACCESS_TOKEN_EXPIRED);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    } catch (JWTVerificationException e) {
      try {
        log.info("[JWT INFO] Access Token을 검증할 수 없습니다. 로그인페이지로 이동시켜주세요.");
        showErrorLogFormat(response, e, ErrorCode.JWT_ISSUE_ACCESS_TOKEN_NOT_VERIFIED);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }

    // Case 4. Refresh Token을 들고온 경우 유효한지 확인
    try {
      if (jwtRefreshToken != null) {

        log.warn("TEST LOG : refresh Token을 들고온 경우 유효한지 확인");
        boolean isVerified = jwtProvider.isVaildRefreshToken(jwtRefreshToken);

        if (isVerified) {
          log.warn("TEST LOG : refresh Token이 유효합니다 통과");

          String accessTokenHeader = jwtProperties.getAccessTokenHeader();
          String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();

          // 3-1. Redis의 RefreshToken과 비교
          String userId = jwtProvider.getClaimId(jwtRefreshToken);
          String redisKey = refreshTokenHeader + ":" + userId;

          String redisRefreshToken = redisTemplate.opsForValue().get(redisKey)
              .replace(jwtProperties.getBearerType() + " ", "");

          if (redisRefreshToken.equals(jwtRefreshToken)) {
            log.warn("TEST LOG : refresh Token redis에 존재합니다 ");
            // 해당 유저의 AccessToken, RefreshToken 재발급
            String newAccessToken = jwtProvider.createAccessToken(jwtProvider.createUser(userId));
            String newRefreshToken = jwtProvider.createRefreshToken(jwtProvider.createUser(userId));

            // RefreshToken 갱신(Sliding Expiration) + Redis 업데이트
            redisTemplate.opsForValue().set(
                refreshTokenHeader + ":" + userId, // Redis Key
                newRefreshToken, // Redis Value
                jwtProperties.getExpireTime().getRefreshToken().toMillis(), TimeUnit.MILLISECONDS);
            ;

            response.setHeader(accessTokenHeader, newAccessToken);
            response.setHeader(refreshTokenHeader, newRefreshToken);
            return;
          } else {
            // Redis에 저장된 RefreshToken이 없거나 일치하지 않은 경우
            // response.setHttpStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.warn("TEST LOG : Refresh Token이 Redis에 존재하지 않습니다. 로그인페이지로 이동시켜주세요.");

            // Redis에 저장된 RefreshToken이 없거나 일치하지 않는 경우도 만료된 상황으로 처리
            throw new TokenExpiredException(redisRefreshToken, null);
          }
        }
      }
    } catch (TokenExpiredException e) {
      try {
        log.info("[JWT INFO] Refresh Token을 검증할 수 없습니다. 로그인페이지로 이동시켜주세요.");
        showErrorLogFormat(response, e, ErrorCode.JWT_ISSUE_REFRESH_TOKEN_EXPIRED);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    } catch (JWTVerificationException e) {
      try {
        log.info("[JWT INFO] Refresh Token을 검증할 수 없습니다. 로그인페이지로 이동시켜주세요.");
        showErrorLogFormat(response, e, ErrorCode.JWT_ISSUE_REFRESH_TOKEN_NOT_VERIFIED);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }

  }

  /**
   * @기능 : 에러 로그 형식 출력 및 응답 처리
   * @param response  HTTP 응답
   * @param e         발생한 예외
   * @param errorCode 에러 코드
   */
  public void showErrorLogFormat(HttpServletResponse response, Exception e, ErrorCode errorCode) throws Exception {

    log.error(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    // 응답 객체 구성
    ResponseApi<Object> apiResponse = ResponseApi.fail(errorCode);

    // JSON 변환 후 응답
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    objectMapper.writeValue(response.getWriter(), apiResponse);

  }
}
