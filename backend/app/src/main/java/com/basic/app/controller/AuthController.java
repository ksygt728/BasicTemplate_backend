
/**
 * @파일명   : UserController.java
 * @설명     : 사용자 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.31
 * @변경이력 :
 *   2025.07.31     김승연       최초 생성
 */

package com.basic.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.AuthReqDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.service.interfaces.AuthService;
import com.basic.app.service.interfaces.SmsService;
import com.basic.app.service.interfaces.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Tag(name = "AuthController", description = "인증 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private SmsService smsService;

  @Autowired
  private JwtProperties jwtProperties;

  /* [REQ_CMN_001] [화면 : 회원가입] [기능 : 이용약관 동의] */
  /* [REQ_CMN_002] [화면 : 회원가입] [기능 : 이용약관 동의] */

  /* [REQ_CMN_003] [화면 : 회원가입] [기능 : 회원정보 입력] */
  @Operation(summary = "[REQ_CMN_003] [화면 : 회원가입] [기능 : 회원정보 입력]", description = "사용자 회원가입을 처리합니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/signUp")
  public ResponseEntity<ResponseApi<Map<String, Object>>> signUp(
      @Validated(CreateGroup.class) UserReqDto user) {
    Map<String, Object> data = authService.signUp(user);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_CMN_004] [화면 : 로그인] [기능 : 일반 로그인(성공)] */
  @Operation(summary = "[REQ_CMN_004] [화면 : 로그인] [기능 : 일반 로그인(성공)]", description = "사용자 로그인을 처리합니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/signIn")
  public ResponseEntity<ResponseApi<Map<String, Object>>> signIn(
      @Validated(CreateGroup.class) AuthReqDto user) {

    // 로그인 처리
    Map<String, Object> data = authService.signIn(user);

    // 헤더정보 가져오기
    String accessTokenHeader = jwtProperties.getAccessTokenHeader();
    String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();

    // JWT 토큰 가져오기
    String jwtAccessToken = data.get(accessTokenHeader).toString();
    String jwtRefreshToken = data.get(refreshTokenHeader).toString();

    return ResponseEntity.status(HttpStatus.OK)
        .header(accessTokenHeader, jwtAccessToken)
        .header(refreshTokenHeader, jwtRefreshToken)
        .body(ResponseApi.success(null));
  }

  /* [REQ_CMN_005] [화면 : 로그인] [기능 : 프론트엔드 임시 기능]- 직접 구현해본 코드고, 사용X */
  // 원래 프론트엔드 코드인데 없어서 임시로 만듦
  @Operation(summary = "[REQ_CMN_004] [화면 : 로그인] [기능 : 프론트엔드 임시 기능]", description = "")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/kakao-callback")
  public ResponseEntity<ResponseApi<Map<String, Object>>> kakaoCallback(String code, String state) {

    Map<String, Object> data = new HashMap<>();

    data.put("code", code);
    data.put("state", state);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseApi.success(data));

  }

  /* [REQ_CMN_005] [화면 : 로그인] [기능 : 카카오 계정 로그인] - 직접 구현해본 코드고, 사용X */
  @Operation(summary = "[REQ_CMN_004] [화면 : 로그인] [기능 : 카카오 안중(성공)]", description = "사용자 로그인을 처리합니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/kakao-signIn")
  public ResponseEntity<ResponseApi<Map<String, Object>>> signInForKakao(String code, String state) {

    Map<String, Object> data = authService.signInForKakao(code);

    // // 헤더정보 가져오기
    String accessTokenHeader = jwtProperties.getAccessTokenHeader();
    String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();

    // // JWT 토큰 가져오기
    String jwtAccessToken = data.get(accessTokenHeader).toString();
    String jwtRefreshToken = data.get(refreshTokenHeader).toString();

    return ResponseEntity.status(HttpStatus.OK)
        .header(accessTokenHeader, jwtAccessToken)
        .header(refreshTokenHeader, jwtRefreshToken)
        .body(ResponseApi.success(null));

  }

  /* [REQ_CMN_009] [화면 : 로그인] [기능 : 자동 로그인] */
  /* [REQ_CMN_010] [화면 : 로그인] [기능 : 아이디 저장] */
  /* [REQ_CMN_011] [화면 : 로그인] [기능 : 로그인 > 아이디 찾기] */
  /* [REQ_CMN_012] [화면 : 로그인] [기능 : 로그인 > 비밀번호 찾기] */
  /* [REQ_CMN_013] [화면 : 로그인] [기능 : 로그인 > 비밀번호 찾기 > 비밀번호 초기화] */

  @PreAuthorize("isAuthenticated() and (#user.userId == authentication.name or hasRole('ADMIN'))")
  @PostMapping("/test")
  public ResponseEntity<ResponseApi<Map<String, Object>>> test(
      @Validated(CreateGroup.class) AuthReqDto user) {

    // 로그인 처리
    Map<String, Object> data = authService.signIn(user);

    String test = "응답하면 정상";
    data.put("data", test);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseApi.success(data));
  }

  /* [채번예정] [화면 : 로그인] [기능 : 문자 인증번호 발송 요청] */
  @Operation(summary = "[채번예정] [화면 : 로그인] [기능 : 문자 인증번호 발송 요청]", description = "문자로 인증번호를 발송하는 로직")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/smsAuth")
  public ResponseEntity<ResponseApi<Map<String, Object>>> smsAuth(String phoneNum) {
    Map<String, Object> data = smsService.smsAuth(phoneNum);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseApi.success(data));
  }

  /* [채번예정] [화면 : 로그인] [기능 : 문자 인증번호 검증] */
  @Operation(summary = "[채번예정] [화면 : 로그인] [기능 : 문자 인증번호 검증]", description = "인증번호를 입력후 검증하는 로직")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/smsAuthValidation")
  public ResponseEntity<ResponseApi<Map<String, Object>>> smsAuthValidation(String phoneNum, String smsCode) {
    Map<String, Object> data = smsService.smsAuthValidation(phoneNum, smsCode);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseApi.success(data));
  }

}