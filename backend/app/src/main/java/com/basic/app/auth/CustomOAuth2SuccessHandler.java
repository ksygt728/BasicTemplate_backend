
package com.basic.app.auth;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.responseDto.specialDto.AuthResDto;
import com.basic.app.entity.Role;
import com.basic.app.entity.RoleUser;
import com.basic.app.entity.User;
import com.basic.app.entity.compositeKey.RoleUserId;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.jwt.JwtProvider;
import com.basic.app.repository.RoleRepository;
import com.basic.app.repository.RoleUserRepository;
import com.basic.app.repository.UserRepository;
import com.basic.app.util.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : CustomOAuth2SuccessHandler.java
 * @설명 : OAuth2 로그인 성공 핸들러
 * @Url :
 *      [중요!!] 시크릿탭에서 아래 URL을 브라우저(front-end)에서 호출하여 OAuth2 로그인 시작해야한다.
 *      이후, 인증까지 모두 Spring Security가 처리하고 성공 시 해당 success handler가 호출된다.
 * 
 *      kakao : http://localhost:8080/oauth2/authorization/kakao
 *      google : http://localhost:8080/oauth2/authorization/google
 *      naver : http://localhost:8080/oauth2/authorization/naver
 * @Step :
 * 
 *       [OAuth2 로그인 절차]
 *       ** -- Spring Security가 자동으로 처리(1~5) --**
 *       1. 로그인 시작(프론트엔드에서 각 Oauth2 로그인 URL로 리다이렉트)
 *       2. 인증 성공됨(인증 code를 받아옴)
 *       3. code로 access token 발급 요청
 *       4. access token으로 사용자 정보 요청
 *       5. Security Context에 인증 정보 저장
 *       ** --이후 CustomOAuth2SuccessHandler가 호출됨(직접 처리 필요) --**
 *       S. Security Context에 저장되어 있던 인증정보 삭제(시스템에 맞는 인증정보 재등록 예정)
 *       6. User 테이블에 해당 이메일이 존재하는지 확인하여 회원가입 or 매핑처리
 *       - 6-1. 없으면 자동 회원가입처리
 *       - 6-2. 있으면 계정 연동 처리
 *       7. 사용자정보로 JWT 토큰 발급 및 SevurityContext에 등록
 *       8. 시스템에서 발급한 Access&Refresh Token 헤더 응답 + (redis에 refresh token 저장)
 *       9. 로그인 성공한 유저정보를 Map에 담아 반환 + 프론트엔드 콜백 URL로 리다이렉트
 * 
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 *       2025.12.01 김승연 Step 6-1, 6-2 세부기능 구현 및 주석 보완
 *       2025.12.06 김승연 Step 9 프론트엔드 콜백 URL 리다이렉트 기능 구현
 *       2025.12.14 김승연 Step 6-1, 6-2 RoleUser 매핑처리 리팩토링(RBAC방식 반영)
 * 
 * @테스트케이스 : Step 6에 대한 테스트 케이스
 * 
 *         [[Case1 일반]]
 *         [회원가입]
 *         1. 정상처리(CBMS확인)
 *         2. 이메일중복 : UserType 상관없이 모두 예외처리 되어야 함. (CBMS, 다른거 모두 테스트) -> Pass
 *         3. ID중복 : UserType 상관없이 모두 예외처리 되어야 함. (CBMS, 다른거 모두 테스트) -> Pass
 * 
 *         [로그인]
 *         1. UserType = CBMS : 정상 로그인 처리 -> JWT 토큰 발급 -> 인가처리(TB_ROLE_USER기준
 *         일반사용자,
 *         관리자) -> Pass
 *         2. UserType = KAKAO : 로그인정보 없음으로 실패 -> Pass
 * 
 *         [[Case2 Oauth2.0 회원가입->로그인]]
 *         1. 정상 회원가입(UserType = CBMS확인)
 *         1-1. 이메일 정보가 있는 경우 : 계정연동(기존정보 유지, UserType = KAKAO로 변경) -> 로그인 ->
 *         JWT
 *         토큰 발급
 *         인가처리(TB_ROLE_USER기준 일반사용자,관리자)
 *         1-2. 이메일 정보가 없는 경우 : User정보 insert(UserType = KAKAO) -> 로그인 -> JWT 토큰
 *         발급
 *         처리(TB_ROLE_USER기준 일반사용자,
 *         관리자) -> Pass
 * 
 * 
 *         etc. ID중복되는일은 없음 -> 신규회원이면 insert되는 거고(1-2), 기존회원이면 1-1로 처리됨
 *         --> 정상적으로 토큰만 발급되는지를 확인하면 됨
 * 
 */

@Log4j2
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${front-end.url}")
    private String frontEndUrl;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    /* [REQ_CMN_006] [화면 : 로그인] [기능 : 구글 계정 로그인] */
    /* [REQ_CMN_007] [화면 : 로그인] [기능 : 네이버 계정 로그인] */
    /* [REQ_CMN_008] [화면 : 로그인] [기능 : 페이스북 계정 로그인] */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        /* Step 5. Security Context에 저장되어 있던 인증정보 삭제(시스템에 맞는 인증정보 재등록 예정) */
        SecurityContextHolder.clearContext();

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String provider = authToken.getAuthorizedClientRegistrationId(); // "google", "kakao", "naver" 등
        OAuth2User oAuth2User = authToken.getPrincipal(); // OAuth2User 객체(Json데이터와 동일)
        // Map<String, Object> map = oAuth2User.getAttributes(); // OAuth2 Json 데이터

        String providerId;
        String email = null;

        switch (provider) {
            case "google":
                providerId = oAuth2User.getAttribute("sub").toString();
                email = oAuth2User.getAttribute("email").toString();
                break;
            case "kakao":
                providerId = oAuth2User.getAttribute("id").toString();
                Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
                email = (String) kakaoAccount.get("email");
                break;
            case "naver":
                Map<String, Object> naverResponse = oAuth2User.getAttribute("response");
                providerId = (String) naverResponse.get("id").toString().substring(0, 20);
                email = (String) naverResponse.get("email");
                break;
            default:
                throw new BusinessException(ErrorCode.UNSUPPORTED_PROVIDER, provider);
        }

        /* Step 6. User 테이블에 해당 이메일이 존재하는지 확인하여 회원가입 or 매핑처리 */
        User userEntity = userRepository.findByEmail(email)
                .filter(entity -> entity.getSts().equals(Status.POSITIVE))
                .orElse(null);

        if (userEntity == null) {
            // Step 3-1. 없으면 자동 회원가입처리
            log.info("[Oauth Service] : 해당 이메일로 가입된 사용자가 없어 자동 회원가입처리합니다.");
            // 1. User 테이블에 INSERT
            userEntity = User.builder()
                    .userId(provider + "_" + providerId)
                    .password("N/A") // 카카오 로그인은 패스워드 없음
                    .email(email)
                    .name("N/A") // 이름 정보 없음
                    .phoneNum("N/A") // 카카오 로그인은 전화번호 없음
                    .role("ROLE_GUEST") // 기본 권한 USER
                    .userType(provider) // 카카오 로그인
                    .gender("-") // 성별 정보 없음
                    .department(null)
                    .build();

            userRepository.save(userEntity);

            // // 2. RoleUser 테이블에 INSERT (기본 권한 USER 부여)
            Role basicRole = roleRepository.findById("ROLE_GUEST")
                    .filter(entity -> entity.getSts().equals(Status.POSITIVE))
                    .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_FOUND,
                            "ROLE_GUEST"));

            RoleUser roleUser = RoleUser.builder()
                    .roleUserId(new RoleUserId(basicRole.getRoleCd(), userEntity.getUserId()))
                    .user(userEntity)
                    .role(basicRole)
                    .useYn("Y")
                    .build();

            roleUserRepository.save(roleUser);

        } else {
            // Step 3-2. 있으면 계정 연동 처리
            if (!userEntity.getUserType().equals(provider)) {
                userEntity.setUserType(provider);
                userRepository.save(userEntity);
            }

        }

        /*
         * Step 7. 사용자정보로 JWT 토큰 발급 및 SevurityContext에 등록
         * (인증은 카카오에서 이미 완료했기 때문에 시스템에 맞는 인증정보생성 및 토큰응답만 하면 됨)
         */
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userEntity.getUserId(),
                userEntity.getPassword());

        auth.setDetails(userEntity);

        SecurityContextHolder.getContext().setAuthentication(auth);

        /*
         * Step 8. 시스템에서 발급한 Access&Refresh Token 헤더 응답 + (redis에 refresh token 저장)
         * (카카오 accesstoken은 일회성)
         */

        // -- Redis에 Refresh Token 저장
        String accessTokenHeader = jwtProperties.getAccessTokenHeader();
        String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();
        String accessToken = jwtProvider
                .createAccessToken(jwtProvider.createUserCustomUserDetails(userEntity.getUserId()));
        String refreshToken = jwtProvider
                .createRefreshToken(jwtProvider.createUserCustomUserDetails(userEntity.getUserId()));

        redisTemplate.opsForValue().set(
                refreshTokenHeader + ":" + userEntity.getUserId(), // Redis Key
                refreshToken, // Redis Value
                jwtProperties.getExpireTime().getRefreshToken().toMillis(), TimeUnit.MILLISECONDS) // 만료시간 설정(자동삭제)
        ;

        /* Step 9. 로그인 성공한 유저정보를 Map에 담아 반환 + 프론트엔드 콜백 URL로 리다이렉트 */

        // ModelMapper 대신 직접 DTO 생성 (LAZY 로딩 문제 방지)
        AuthResDto authResDto = AuthResDto.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .phoneNum(userEntity.getPhoneNum())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .userType(userEntity.getUserType())
                .gender(userEntity.getGender())
                .department(null) // OAuth2 로그인 시에는 department 정보 제외
                .build();

        ResponseApi<AuthResDto> responseApi = ResponseApi.success(authResDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String userDataJson = objectMapper.writeValueAsString(responseApi);
        String encodedUserData = java.net.URLEncoder.encode(userDataJson, "UTF-8");

        // 프론트엔드 콜백 URL로 리다이렉트 (토큰과 사용자 정보 모두 URL 파라미터로 전달)
        String redirectUrl = frontEndUrl + "/auth/callback?success=true" +
                "&accessToken=" + java.net.URLEncoder.encode(accessToken, "UTF-8") +
                "&refreshToken=" + java.net.URLEncoder.encode(refreshToken, "UTF-8") +
                "&userData=" + encodedUserData;

        // log.info("[OAuth2 Success] Redirecting to: {}", frontEndUrl +
        // "/auth/callback?success=true");
        // log.info("[OAuth2 Success] Access Token: {}", accessToken);
        // log.info("[OAuth2 Success] Refresh Token: {}", refreshToken);
        // log.info("[OAuth2 Success] User Data: {}", userDataJson);

        response.sendRedirect(redirectUrl);

    }
}