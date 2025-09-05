
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.DepartmentReqDto;

/**
 * @파일명 : DepartmentService.java
 * @설명 : 부서 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */

public interface DepartmentService {

  /**
   * @기능 : 관리자용 부서 전체 목록 조회 (페이징)
   * @param departmentReqDto 부서 검색 조건 DTO
   * @param pageable         페이징 정보
   * @return 부서 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllDepartmentForAdmin(DepartmentReqDto departmentReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 부서 상세 조회
   * @param deptCode 부서 코드
   * @return 부서 상세 정보가 담긴 Map
   */
  Map<String, Object> findByDepartmentForAdmin(String deptCode);

  /**
   * @기능 : 관리자용 부서 등록
   * @param department 부서 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertDepartmentForAdmin(DepartmentReqDto department);

  /**
   * @기능 : 관리자용 부서 수정
   * @param department 부서 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateDepartmentForAdmin(DepartmentReqDto department);

  /**
   * @기능 : 관리자용 부서 삭제
   * @param deptCode 부서 코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteDepartmentForAdmin(String deptCode);

}
