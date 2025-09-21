
package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.RoleMenuReqDto;
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.dto.requestDto.UserReqDto;

/**
 * @파일명 : RoleService.java
 * @설명 : 역할 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */

public interface RoleService {

  /**
   * @기능 : 관리자용 역할 전체 목록 조회 (페이징)
   * @param roleReqDto 역할 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 역할 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllRoleForAdmin(RoleReqDto roleReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 역할 상세 조회
   * @param roleCd 역할 코드
   * @return 역할 상세 정보가 담긴 Map
   */
  Map<String, Object> findByRoleForAdmin(String roleCd);

  /**
   * @기능 : 관리자용 역할 등록
   * @param role 역할 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertRoleForAdmin(RoleReqDto role);

  /**
   * @기능 : 관리자용 역할 수정
   * @param role 역할 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateRoleForAdmin(RoleReqDto role);

  /**
   * @기능 : 관리자용 역할 삭제
   * @param roleCd 역할 코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteRoleForAdmin(String roleCd);

  /**
   * @기능 : 관리자용 특정 역할의 메뉴 권한 조회
   * @param roleCd 역할 코드
   * @return 역할별 메뉴 권한 정보가 담긴 Map
   */
  Map<String, Object> findByRoleMenuForAdmin(String roleCd);

  /**
   * @기능 : 관리자용 역할별 메뉴 권한 수정
   * @param roleMenuReqDtoList 역할-메뉴 수정 요청 DTO 목록
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateRoleMenuForAdmin(List<RoleMenuReqDto> roleMenuReqDtoList);

  /**
   * @기능 : 관리자용 특정 사용자의 역할 목록 조회 (페이징)
   * @param userId   사용자 ID
   * @param pageable 페이징 정보
   * @return 사용자별 역할 목록 정보가 담긴 Map
   */
  Map<String, Object> findByRoleUserForAdmin(String userId, Pageable pageable);

  /**
   * @기능 : 관리자용 전체 사용자 역할 목록 조회 (페이징)
   * @param userReqDto 사용자 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 전체 사용자 역할 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllRoleUserForAdmin(UserReqDto userReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 사용자 역할 등록
   * @param roleUserReqDtoList 사용자-역할 등록 요청 DTO 목록
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertRoleUserForAdmin(List<RoleUserReqDto> roleUserReqDtoList);

  /**
   * @기능 : 관리자용 사용자 역할 삭제
   * @param roleUserReqDtoList 사용자-역할 삭제 요청 DTO 목록
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteRoleUserForAdmin(List<RoleUserReqDto> roleUserReqDtoList);

}
