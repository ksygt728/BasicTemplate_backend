package com.basic.app.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.basic.app.auth.CustomUserDetails;
import com.basic.app.auth.rbac.PermissionKey;
import com.basic.app.controller.AdminApprovalController;
import com.basic.app.entity.RoleMenu;
import com.basic.app.entity.RoleUser;
import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.JwtExeption;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.UserRepository;
import com.basic.app.util.Status;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : JwtProvider.java
 * @설명 : JWT 토큰 생성 및 검증을 담당하는 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 *       2025.12.14 김승연 RBAC방식의 권한 체크로 인한 리팩토링
 */
@Component
@RequiredArgsConstructor
@Transactional
@Log4j2
public class JwtProvider {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private JwtProperties jwtProperties;

  /**
   * @기능 : Access Token 생성
   * @param user 사용자 정보
   * @return 생성된 Access Token
   */
  public String createAccessToken(CustomUserDetails user) {
    return jwtProperties.getBearerType() + " " + JWT.create()
        .withSubject(jwtProperties.getSubject().getFirst())
        .withIssuer(jwtProperties.getIssuer())
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpireTime().getAccessToken().toMillis()))
        .withClaim(jwtProperties.getSubject().getSecond(), user.getUser().getUserId())
        .withClaim(jwtProperties.getSubject().getThird(), user.getUser().getPhoneNum())
        .withClaim(jwtProperties.getSubject().getFourth(), user.getRoles())
        .withClaim(jwtProperties.getSubject().getFifth(), user.getPermissions())
        .sign(Algorithm.HMAC512(jwtProperties.getSecretKey()));
  }

  /**
   * @기능 : Refresh Token 생성
   * @param user 사용자 정보
   * @return 생성된 Refresh Token
   */
  @Transactional
  public String createRefreshToken(CustomUserDetails user) {
    return jwtProperties.getBearerType() + " " + JWT.create()
        .withSubject(jwtProperties.getSubject().getFirst())
        .withIssuer(jwtProperties.getIssuer())
        .withIssuedAt(new Date())
        .withExpiresAt(
            new Date(System.currentTimeMillis() + jwtProperties.getExpireTime().getRefreshToken().toMillis()))
        .withClaim(jwtProperties.getSubject().getSecond(), user.getUser().getUserId())
        .withClaim(jwtProperties.getSubject().getThird(), user.getUser().getPhoneNum())
        .withClaim(jwtProperties.getSubject().getFourth(), user.getRoles())
        .withClaim(jwtProperties.getSubject().getFifth(), user.getPermissions())
        .sign(Algorithm.HMAC512(jwtProperties.getSecretKey()));
  }

  /**
   * @기능 : Header에서 Access Token 가져오기
   * @param request HTTP 요청
   * @return Access Token 또는 null
   */
  public String resolveAccessToken(HttpServletRequest request) {
    String jwtAccessToken = request.getHeader(jwtProperties.getAccessTokenHeader());

    // Header가 있는지 확인
    return (jwtAccessToken == null || !jwtAccessToken.startsWith(jwtProperties.getBearerType())) ? null
        : jwtAccessToken.replace(jwtProperties.getBearerType() + " ", "");

  }

  /**
   * @기능 : Header에서 Refresh Token 가져오기
   * @param request HTTP 요청
   * @return Refresh Token 또는 null
   */
  public String resolveRefreshToken(HttpServletRequest request) {
    String jwtRefreshToken = request.getHeader(jwtProperties.getRefreshTokenHeader());

    // Header가 있는지 확인
    return (jwtRefreshToken == null || !jwtRefreshToken.startsWith(jwtProperties.getBearerType())) ? null
        : jwtRefreshToken.replace(jwtProperties.getBearerType() + " ", "");

  }

  /**
   * @기능 : Access Token 만료되었는지 확인
   * @param jwtToken JWT 토큰
   * @return 유효성 검증 결과
   */
  public boolean isVaildAccessToken(String jwtToken) {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtProperties.getSecretKey())).build().verify(jwtToken);

    return true; // verify함수에서 검증이 안되면 에러를 던디기 때문에 무조건 return true (에러처리는 함수밖에서 try-catch로 처리함)

  }

  /**
   * @기능 : Refresh Token 만료되었는지 확인
   * @param jwtToken JWT 토큰
   * @return 유효성 검증 결과
   */
  public boolean isVaildRefreshToken(String jwtToken) {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtProperties.getSecretKey())).build().verify(jwtToken);

    return true; // verify함수에서 검증이 안되면 에러를 던디기 때문에 무조건 return true (에러처리는 함수밖에서 try-catch로 처리함)

  }

  /**
   * @기능 : 서명을 통해 Token 의 ID 가져오기
   * @param jwtToken JWT 토큰
   * @return 사용자 ID
   */
  public String getClaimId(String jwtToken) {
    String result = null;
    try {
      result = JWT.require(Algorithm.HMAC512(jwtProperties.getSecretKey())).build().verify(jwtToken).getClaim("userId")
          .asString();
      return result;
    } catch (TokenExpiredException e) {
      throw new JwtExeption(ErrorCode.JWT_ISSUE_ACCESS_TOKEN_EXPIRED); // 토큰 만료
    } catch (JWTVerificationException e) {
      throw new JwtExeption(ErrorCode.JWT_ISSUE_ACCESS_TOKEN_NOT_VERIFIED);// 서명이 되지 않음
    }
  }

  /**
   * @기능 : 인증 정보 가져오기
   * @param jwtToken 사용자 ID
   * @설명 : authentication 객체 생성용 CumtomerUserDetails정보를 세팅합니다.
   *     1. 유저정보(user)
   *     2. 역할정보(role)
   *     3. 권한정보(permissions)
   * 
   * @return 인증 토큰
   */
  public UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {

    String userId = null;
    List<String> roles = new ArrayList<>();
    List<String> permissions = new ArrayList<>();

    try {
      userId = JWT.require(Algorithm.HMAC512(jwtProperties.getSecretKey())).build().verify(jwtToken).getClaim("userId")
          .asString();

      roles = JWT.require(Algorithm.HMAC512(jwtProperties.getSecretKey())).build().verify(jwtToken).getClaim("roles")
          .asList(String.class);

      permissions = JWT.require(Algorithm.HMAC512(jwtProperties.getSecretKey())).build().verify(jwtToken)
          .getClaim("permissions")
          .asList(String.class);

    } catch (TokenExpiredException e) {
      throw new JwtExeption(ErrorCode.JWT_ISSUE_ACCESS_TOKEN_EXPIRED); // 토큰 만료
    } catch (JWTVerificationException e) {
      throw new JwtExeption(ErrorCode.JWT_ISSUE_ACCESS_TOKEN_NOT_VERIFIED);// 서명이 되지 않음
    }

    User user = userRepository.findById(userId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    CustomUserDetails userDetails = new CustomUserDetails(user, roles, permissions);

    /* JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체생성 */
    return new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
  }

  /**
   * @기능 : CustomUserDetails 엔티티 생성
   * @param userId 사용자 ID
   * @return CustomUserDetails 엔티티
   */
  public CustomUserDetails createUserCustomUserDetails(String userId) {

    // [1. 사용자 정보 조회]
    User user = userRepository.findById(userId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // [2. 역할 및 권한 설정]
    List<String> roles = user.getRoleUsers().stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE) && entity.getUseYn().equals("Y"))
        .map(entity -> entity.getRoleUserId().getRoleCd())
        .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    roles.add(user.getRole()); // 기본 권한도 추가
    roles = roles.stream().distinct().collect(java.util.stream.Collectors.toList());

    // [3. 권한 설정]
    List<String> permissions = new ArrayList<>();

    // /* Step 3-1 : User가 가지고 있는 Role List 가져오기 */
    List<RoleUser> roleUserList = user.getRoleUsers().stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE) &&
            entity.getUseYn().equals("Y"))
        // .map(entity -> entity.getRoleUserId().getRoleCd())
        .toList();

    /* Step 3-2 : Role List가 가지고 있는 메뉴별 권한 가져오기(겹칠 수 있으니 중복제거) */
    List<RoleMenu> roleMenuList = roleUserList.stream()
        .flatMap(roleUser -> roleUser.getRole().getRoleMenus().stream())
        .filter(roleMenu -> roleMenu.getSts().equals(Status.POSITIVE) &&
            roleMenu.getUseYn().equals("Y"))
        // .map(roleMenu -> roleMenu.getMenu().getMenuCd())
        .distinct()
        .toList();

    /* Step 3-3 : 메뉴별 권한 리스트를 가지고 PermissionKey생성(JWT사용) */
    permissions = roleMenuList.stream()
        .flatMap(roleMenu -> PermissionKey.of(roleMenu.getRoleMenuId().getMenuCd(),
            roleMenu.getMenuRw()).stream())
        .toList();

    return new CustomUserDetails(user, roles, permissions);
  }
}
