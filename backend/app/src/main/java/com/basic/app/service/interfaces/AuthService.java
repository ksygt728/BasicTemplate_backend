
package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.AuthReqDto;

/**
 * @파일명 : AuthService.java
 * @설명 : 인증 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface AuthService {

  /**
   * @기능 : 카카오 로그인 처리
   * @param code 카카오 인증 코드
   * @return 로그인 결과 정보가 담긴 Map
   */
  Map<String, Object> signInForKakao(String code);

  /**
   * @기능 : 회원가입 처리
   * @param userReqDto 사용자 등록 요청 DTO
   * @return 회원가입 결과 정보가 담긴 Map
   */
  Map<String, Object> signUp(UserReqDto userReqDto);

  /**
   * @기능 : 일반 로그인 처리
   * @param user 인증 요청 DTO
   * @return 로그인 결과 정보가 담긴 Map
   */
  Map<String, Object> signIn(AuthReqDto user);

  /**
   * @기능 : 사용자정보 조회
   * @설명 : SecurityContext에서 인증된 사용자 정보 조회해서 반환(프론트엔드 새로고침 or 페이지 이동 시 Redux의 사용자
   *     정보가 날아가는 문제 대응)
   * @return 로그인 결과 정보가 담긴 Map
   */
  Map<String, Object> getCurrentUser();

}
