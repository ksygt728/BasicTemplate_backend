
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.InterfaceReqDto;

/**
 * @파일명 : InterfaceService.java
 * @설명 : 인터페이스 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface InterfaceService {

  /**
   * @기능 : 관리자용 인터페이스 전체 목록 조회 (페이징)
   * @param pageable 페이징 정보
   * @return 인터페이스 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllInterfaceForAdmin(Pageable pageable);

  /**
   * @기능 : 관리자용 특정 인터페이스 상세 조회
   * @param ifId 인터페이스 ID
   * @return 인터페이스 상세 정보가 담긴 Map
   */
  Map<String, Object> findByInterfaceForAdmin(String ifId);

  /**
   * @기능 : 관리자용 인터페이스 실행
   * @param ifc 인터페이스 실행 요청 DTO
   * @return 실행 결과 정보가 담긴 Map
   */
  Map<String, Object> executeInterfaceForAdmin(InterfaceReqDto ifc);

  /**
   * @기능 : 관리자용 인터페이스 실행 이력 조회
   * @param ifId 인터페이스 ID
   * @return 실행 이력 정보가 담긴 Map
   */
  Map<String, Object> findByInterfaceHistoryForAdmin(String ifId);

  /**
   * @기능 : 관리자용 인터페이스 등록
   * @param ifc 인터페이스 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertInterfaceForAdmin(InterfaceReqDto ifc);

  /**
   * @기능 : 관리자용 인터페이스 수정
   * @param ifc 인터페이스 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateInterfaceForAdmin(InterfaceReqDto ifc);

  /**
   * @기능 : 관리자용 인터페이스 삭제
   * @param ifId 인터페이스 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteInterfaceForAdmin(String ifId);

  /**
   * @기능 : 관리자용 조건별 인터페이스 목록 조회 (페이징)
   * @param interfaceReqDto 인터페이스 검색 조건 DTO
   * @param pageable        페이징 정보
   * @return 인터페이스 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllInterfaceWithConditionsForAdmin(InterfaceReqDto interfaceReqDto, Pageable pageable);

}
