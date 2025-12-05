package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.AuthReqDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.dto.responseDto.specialDto.AuthResDto;
import com.basic.app.entity.Department;
import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.exception.customException.SystemErrorException;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.jwt.JwtProvider;
import com.basic.app.repository.DepartmentRepository;
import com.basic.app.repository.UserRepository;
import com.basic.app.service.interfaces.AuthService;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : AuthServiceImpl.java
 * @설명 : 인증 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Log4j2
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private PasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private JwtProperties jwtProperties;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  UserRepository userRepository;

  @Autowired
  DepartmentRepository departmentRepository;

  // -- 사용 안함 --
  // @Value("${spring.OAuth.kakao.api-key}")
  private String API_KEY;

  // @Value("${spring.OAuth.kakao.client-secret-key}")
  private String KAKAO_CLIENT_SECRET_KEY;

  // @Value("${spring.OAuth.kakao.redirect-uri}")
  private String KAAKOO_REDIRECT_URI;

  // @Value("${spring.OAuth.kakao.access-token-uri}")
  private String KAKAO_ACCESS_TOKEN_URI;

  // @Value("${spring.OAuth.kakao.user-info-uri}")
  private String KAKAO_USER_INFO_URI;

  /**
   * @기능 : 회원가입 처리
   * @param userReqDto 회원가입 요청 DTO
   * @return 회원가입 결과 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> signUp(UserReqDto userReqDto) {

    Map<String, Object> data = new HashMap<>();
    String deptCode = userReqDto.getDeptCode();

    // 1. DTO -> Entity 변환
    User userEntity = userReqDto.toEntity(User.class);

    // 2. ID로 기존 엔티티 조회
    // 2-1. ID 중복 확인
    userRepository.findById(userEntity.getUserId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.USER_DUPLICATE);
        });

    // 2-2. 이메일 중복 확인
    userRepository.findByEmail(userEntity.getEmail())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
        });

    // 3. 데이터 저장
    Department department = departmentRepository.findById(deptCode)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword())); // 비밀번호 암호화
    userEntity.setRole("ROLE_GUEST"); // 기본 역할 설정
    userEntity.setUserType("CBMS"); // 기본 회원유형 설정
    userEntity.setDepartment(department); // 기본 부서 설정
    User savedUserEntity = userRepository.save(userEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedUserEntity.toDto(UserResDto.class));
    return data;
  }

  /**
   * @기능 : 일반 로그인 처리
   * @param user 로그인 요청 DTO
   * @return 로그인 결과(Access/Refresh Token 등)가 담긴 Map
   */
  @Override
  public Map<String, Object> signIn(AuthReqDto user) {
    Map<String, Object> data = new HashMap<>();

    String userId = user.getUserId();
    String password = user.getPassword();

    // 1. DB에 데이터가 있는지 조회
    User authenticatedUser = userRepository.findById(userId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE) && entity.getUserType().equals("CBMS"))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 인증토큰 생성
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);

    // 3. 로그인 시도
    /*
     * - CustomUserDetailsService loadUserbyUsername()이 실행
     * authentication이 정상적으로 return 되면 DB에 있는 ID와 password가 일치한 것 = 인증완료
     * 인증 성공 후 자동으로 SecurityContext에 인증객체 저장
     */
    Authentication authentication;
    authentication = authenticationManager.authenticate(token);

    // 4. 인증 성공 후 SecurityContext에 인증객체 저장
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 5. JWT 토큰 생성
    String accessTokenHeader = jwtProperties.getAccessTokenHeader();
    String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();

    String accessToken = jwtProvider.createAccessToken(authenticatedUser);
    String refreshToken = jwtProvider.createRefreshToken(authenticatedUser);

    // 6. Redis에 Refresh Token 저장
    redisTemplate.opsForValue().set(
        refreshTokenHeader + ":" + authenticatedUser.getUserId(), // Redis Key
        refreshToken, // Redis Value
        jwtProperties.getExpireTime().getRefreshToken().toMillis(), TimeUnit.MILLISECONDS) // 만료시간 설정(자동삭제)
    ;

    // 7. 로그인 성공한 유저정보를 Map에 담아 반환
    AuthResDto authenticatedUserInfo = ModelMapperUtils.map(authenticatedUser, AuthResDto.class);
    data.put("data", authenticatedUserInfo);

    data.put(accessTokenHeader, accessToken);
    data.put(refreshTokenHeader, refreshToken);

    return data;

  }

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

  /**
   * @기능 : 카카오 로그인 처리
   * @param code 카카오 인증 코드
   * @return 로그인 결과(Access/Refresh Token 등)가 담긴 Map
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

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body,
          headers);

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
            jwtProperties.getExpireTime().getRefreshToken().toMillis(),
            TimeUnit.MILLISECONDS) // 만료시간 설정(자동삭제)
        ;

        /* Step 6. 로그인 성공한 유저정보를 Map에 담아 반환 */
        AuthResDto authenticatedUserInfo = ModelMapperUtils.map(userEntity, AuthResDto.class);
        data.put("data", authenticatedUserInfo);

        data.put(accessTokenHeader, accessToken);
        data.put(refreshTokenHeader, refreshToken);

      }

    } catch (Exception e) {
      throw new SystemErrorException(e, ErrorCode.KAKAO_AUTH_ERROR, "");
    }

    return data;
  }

  /**
   * @기능 : 사용자정보 조회
   * @설명 : SecurityContext에서 인증된 사용자 정보 조회해서 반환(프론트엔드 새로고침 or 페이지 이동 시 Redux의 사용자
   *     정보가 날아가는 문제 대응)
   * @return 로그인 결과 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> getCurrentUser() {

    Map<String, Object> data = new HashMap<>();

    // SecurityContext에서 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new BusinessException(ErrorCode.LOGIN_REQUIRED);
    }

    String userId = authentication.getName(); // JWT에서 추출한 userId

    // DB에서 사용자 정보 조회
    User user = userRepository.findById(userId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.LOGIN_REQUIRED));

    UserResDto userDto = ModelMapperUtils.map(user, UserResDto.class);

    data.put("data", userDto);

    return data;
  }

}