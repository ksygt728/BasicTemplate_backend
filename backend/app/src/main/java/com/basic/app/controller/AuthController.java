
/**
 * @파일명   : UserController.java
 * @설명     : 사용자 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.31
 * @변경이력 :
 *   2025.07.31     김승연       최초 생성
 */

package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.AuthReqDto;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.service.interfaces.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtProperties jwtProperties;

  /* [REQ_CMN_001] [화면 : 회원가입] [기능 : 이용약관 동의] */
  /* [REQ_CMN_002] [화면 : 회원가입] [기능 : 이용약관 동의] */

  /* [REQ_CMN_003] [화면 : 회원가입] [기능 : 회원정보 입력] */
  @PostMapping("/signUp")
  public ResponseEntity<ApiResponse<Map<String, Object>>> signUp(
      @Validated(CreateGroup.class) UserReqDto user) {
    Map<String, Object> data = userService.signUp(user);

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_CMN_004] [화면 : 로그인] [기능 : 일반 로그인(성공)] */
  @PostMapping("/signIn")
  public ResponseEntity<ApiResponse<Map<String, Object>>> signIn(
      @Validated(CreateGroup.class) AuthReqDto user) {

    // 로그인 처리
    Map<String, Object> data = userService.signIn(user);

    // 헤더정보 가져오기
    String accessTokenHeader = jwtProperties.getAccessTokenHeader();
    String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();

    // JWT 토큰 가져오기
    String jwtAccessToken = data.get(accessTokenHeader).toString();
    String jwtRefreshToken = data.get(refreshTokenHeader).toString();

    return ResponseEntity.status(HttpStatus.OK)
        .header(accessTokenHeader, jwtAccessToken)
        .header(refreshTokenHeader, jwtRefreshToken)
        .body(ApiResponse.success(null));
  }

  /* [REQ_CMN_005] [화면 : 로그인] [기능 : 카카오 계정 로그인] */
  /* [REQ_CMN_006] [화면 : 로그인] [기능 : 구글 계정 로그인] */
  /* [REQ_CMN_007] [화면 : 로그인] [기능 : 네이버 계정 로그인] */
  /* [REQ_CMN_008] [화면 : 로그인] [기능 : 페이스북 계정 로그인] */
  /* [REQ_CMN_009] [화면 : 로그인] [기능 : 자동 로그인] */
  /* [REQ_CMN_010] [화면 : 로그인] [기능 : 아이디 저장] */
  /* [REQ_CMN_011] [화면 : 로그인] [기능 : 로그인 > 아이디 찾기] */
  /* [REQ_CMN_012] [화면 : 로그인] [기능 : 로그인 > 비밀번호 찾기] */
  /* [REQ_CMN_013] [화면 : 로그인] [기능 : 로그인 > 비밀번호 찾기 > 비밀번호 초기화] */

  @PreAuthorize("isAuthenticated() and (#user.userId == authentication.name or hasRole('ADMIN'))")
  @PostMapping("/test")
  public ResponseEntity<ApiResponse<Map<String, Object>>> test(
      @Validated(CreateGroup.class) AuthReqDto user) {

    // 로그인 처리
    Map<String, Object> data = userService.signIn(user);

    String test = "응답하면 정상";
    data.put("data", test);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(data));
  }
}