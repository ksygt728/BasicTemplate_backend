
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.UserReqDto;

/**
 * @파일명 : UserService.java
 * @설명 : 사용자 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */

public interface UserService {

  /**
   * @기능 : 관리자용 사용자 전체 목록 조회 (페이징)
   * @param userReqDto 사용자 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 사용자 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllUserForAdmin(UserReqDto userReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 사용자 상세 조회
   * @param userId 사용자 ID
   * @return 사용자 상세 정보가 담긴 Map
   */
  Map<String, Object> findByUserForAdmin(String userId);

  /**
   * @기능 : 관리자용 사용자 수정
   * @param user 사용자 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateUserForAdmin(UserReqDto user);

  /**
   * @기능 : 관리자용 사용자 삭제
   * @param userId 사용자 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteUserForAdmin(String userId);

}
