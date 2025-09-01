package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.SystemErrorException;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.jwt.JwtProvider;
import com.basic.app.repository.UserRepository;
import com.basic.app.service.interfaces.AuthService;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private JwtProperties jwtProperties;

  @Value("${spring.OAuth.kakao.api-key}")
  private String API_KEY;

  @Value("${spring.OAuth.kakao.client-secret-key}")
  private String KAKAO_CLIENT_SECRET_KEY;

  @Value("${spring.OAuth.kakao.redirect-uri}")
  private String KAAKOO_REDIRECT_URI;

  @Value("${spring.OAuth.kakao.access-token-uri}")
  private String KAKAO_ACCESS_TOKEN_URI;

  @Value("${spring.OAuth.kakao.user-info-uri}")
  private String KAKAO_USER_INFO_URI;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  UserRepository userRepository;

  /*
   * [[Case1 일반]]
   * [회원가입]
   * 1. 정상처리(CBMS확인)
   * 2. 이메일중복 : UserType 상관없이 모두 예외처리 되어야 함. (CBMS, 다른거 모두 테스트) -> Pass
   * 3. ID중복 : UserType 상관없이 모두 예외처리 되어야 함. (CBMS, 다른거 모두 테스트) -> Pass
   * 
   * [로그인]
   * 1. UserType = CBMS : 정상 로그인 처리 -> JWT 토큰 발급 -> 인가처리(TB_ROLE_USER기준 일반사용자,
   * 관리자) -> Pass
   * 2. UserType = KAKAO : 로그인정보 없음으로 실패 -> Pass
   * 
   * [[Case2 Oauth2.0 회원가입->로그인]]
   * 1. 정상 회원가입(UserType = CBMS확인)
   * 1-1. 이메일 정보가 있는 경우 : 계정연동(기존정보 유지, UserType = KAKAO로 변경) -> 로그인 -> JWT 토큰 발급
   * 인가처리(TB_ROLE_USER기준 일반사용자,관리자)
   * 1-2. 이메일 정보가 없는 경우 : User정보 insert(UserType = KAKAO) -> 로그인 -> JWT 토큰 발급
   * 처리(TB_ROLE_USER기준 일반사용자,
   * 관리자) -> Pass
   * 
   * 
   * etc. ID중복되는일은 없음 -> 신규회원이면 insert되는 거고(1-2), 기존회원이면 1-1로 처리됨
   * --> 정상적으로 토큰만 발급되는지를 확인하면 됨
   * 
   * 
   * 
   */

  @Override
  public Map<String, Object> signInForKakao(String code) {

    Map<String, Object> data = new HashMap<>();
    log.info("[KAKAO Authentication code] : {}", code);

    try {

      /* Step 1 : Front End에서 전달받은 Kakao인증코드를 사용해 Kakao AccessToken발급 요청 */
      RestTemplate restTemplate = new RestTemplate();

      // -- Header
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      // -- Body
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("grant_type", "authorization_code");
      body.add("client_id", API_KEY); // REST API KEY
      body.add("client_secret", KAKAO_CLIENT_SECRET_KEY); // Redirect URI
      body.add("redirect_uri", KAAKOO_REDIRECT_URI); // Redirect URI
      body.add("code", code);
      body.add("scope", "talk_message,friends");

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

      // -- POST 요청
      Map<String, Object> response = restTemplate.postForObject(
          KAKAO_ACCESS_TOKEN_URI,
          request,
          HashMap.class);

      /* Step 2. AccessToken 발급받아 카카오 사용자 정보 가져오기 */
      String kakaoAccessToken = response.get("access_token").toString();
      if (kakaoAccessToken != null && !kakaoAccessToken.isEmpty()) {

        log.info("c AccessToken] : {}", kakaoAccessToken);

        // -- Header
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.set("Authorization", "Bearer " + kakaoAccessToken);

        // -- POST 요청
        Map<String, Object> responseInfo = restTemplate.postForObject(
            KAKAO_USER_INFO_URI,
            new HttpEntity<>(null, tokenHeaders),
            HashMap.class);

        Map<String, Object> kakaoAccount = (Map<String, Object>) responseInfo.get("kakao_account");
        String kakaoId = responseInfo.get("id").toString();
        String kakaoEmail = kakaoAccount.get("email").toString();

        /* Step 3. User 테이블에 해당 이메일이 존재하는지 확인하여 회원가입 or 매핑처리 */
        User userEntity = userRepository.findByEmail(kakaoEmail)
            .filter(entity -> entity.getSts().equals(Status.POSITIVE))
            .orElse(null);

        if (userEntity == null) {
          // Step 3-1. 없으면 자동 회원가입처리
          log.info("[KAKAO User] : 해당 이메일로 가입된 사용자가 없어 자동 회원가입처리합니다.");
          // 1. User 테이블에 INSERT
          userEntity = User.builder()
              .userId("kakao|" + kakaoId)
              .password("N/A") // 카카오 로그인은 패스워드 없음
              .email(kakaoEmail)
              .name("N/A") // 이름 정보 없음
              .phoneNum("N/A") // 카카오 로그인은 전화번호 없음
              .role("ROLE_GUEST") // 기본 권한 USER
              .userType("KAKAO") // 카카오 로그인
              .gender("-") // 성별 정보 없음
              .department(null)
              .build();

          userRepository.save(userEntity);
        } else {
          // Step 3-2. 있으면 계정 연동 처리
          if (!userEntity.getUserType().equals("KAKAO")) {
            userEntity.setUserType("KAKAO");
            userRepository.save(userEntity);
          }

        }

        /*
         * Step 4. 사용자정보로 JWT 토큰 발급 및 SevurityContext에 등록
         * (인증은 카카오에서 이미 완료했기 때문에 시스템에 맞는 인증정보생성 및 토큰응답만 하면 됨)
         */
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userEntity.getUserId(),
            userEntity.getPassword());

        authentication.setDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        /*
         * Step 5. 시스템에서 발급한 Access&Refresh Token 헤더 응답
         * (카카오 accesstoken은 일회성)
         */

        // -- Redis에 Refresh Token 저장
        String accessTokenHeader = jwtProperties.getAccessTokenHeader();
        String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();
        String accessToken = jwtProvider.createAccessToken(userEntity);
        String refreshToken = jwtProvider.createRefreshToken(userEntity);

        redisTemplate.opsForValue().set(
            refreshTokenHeader + ":" + userEntity.getUserId(), // Redis Key
            refreshToken, // Redis Value
            jwtProperties.getExpireTime().getRefreshToken().toMillis(), TimeUnit.MILLISECONDS) // 만료시간 설정(자동삭제)
        ;

        /* Step 6. 로그인 성공한 유저정보를 Map에 담아 반환 */
        data.put(accessTokenHeader, accessToken);
        data.put(refreshTokenHeader, refreshToken);

      }

    } catch (Exception e) {
      throw new SystemErrorException(e, ErrorCode.KAKAO_AUTH_ERROR, "");
    }

    return data;
  }

}