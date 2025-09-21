
package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.WebSvcReqDto;

/**
 * @파일명 : WebSvcService.java
 * @설명 : 웹서비스 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface WebSvcService {

  /**
   * @기능 : 관리자용 웹서비스 전체 목록 조회
   * @return 웹서비스 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllWebserviceForAdmin();

  /**
   * @기능 : 관리자용 특정 웹서비스 상세 조회
   * @param svcId 서비스 ID
   * @return 웹서비스 상세 정보가 담긴 Map
   */
  Map<String, Object> findByWebserviceForAdmin(String svcId);

  /**
   * @기능 : 관리자용 웹서비스 등록
   * @param webSvc 웹서비스 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> inesrtWebserviceForAdmin(WebSvcReqDto webSvc);

  /**
   * @기능 : 관리자용 웹서비스 수정
   * @param webSvc 웹서비스 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateWebserviceForAdmin(WebSvcReqDto webSvc);

  /**
   * @기능 : 관리자용 웹서비스 삭제
   * @param svcId 서비스 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteWebserviceForAdmin(String svcId);

  /**
   * @기능 : 관리자용 웹서비스 실행
   * @param svcId 서비스 ID
   * @return 실행 결과 정보가 담긴 Map
   */
  Map<String, Object> executeWebserviceForAdmin(String svcId);

  /**
   * @기능 : 관리자용 웹서비스 실행 이력 조회
   * @param svcId 서비스 ID
   * @return 실행 이력 정보가 담긴 Map
   */
  Map<String, Object> findByWebserviceHistoryForAdmin(String svcId);

}
