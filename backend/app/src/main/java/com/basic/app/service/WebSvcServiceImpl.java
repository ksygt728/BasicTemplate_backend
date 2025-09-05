package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.WebSvcReqDto;
import com.basic.app.service.interfaces.WebSvcService;

/**
 * @파일명 : WebSvcServiceImpl.java
 * @설명 : 웹 서비스 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class WebSvcServiceImpl implements WebSvcService {

  /**
   * @기능 : 관리자용 웹 서비스 전체 목록 조회
   * @return 웹 서비스 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllWebserviceForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllWebserviceForAdmin'");
  }

  /**
   * @기능 : 관리자용 특정 웹 서비스 상세 조회
   * @param svcId 서비스 ID
   * @return 웹 서비스 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByWebserviceForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByWebserviceForAdmin'");
  }

  /**
   * @기능 : 관리자용 웹 서비스 신규 등록
   * @param webSvc 웹 서비스 등록 요청 DTO
   * @return 등록된 웹 서비스 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> inesrtWebserviceForAdmin(WebSvcReqDto webSvc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'inesrtWebserviceForAdmin'");
  }

  /**
   * @기능 : 관리자용 웹 서비스 정보 수정
   * @param webSvc 웹 서비스 수정 요청 DTO
   * @return 수정된 웹 서비스 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> updateWebserviceForAdmin(WebSvcReqDto webSvc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateWebserviceForAdmin'");
  }

  /**
   * @기능 : 관리자용 웹 서비스 삭제
   * @param svcId 삭제할 서비스 ID
   * @return 삭제 성공 메시지가 담긴 Map
   */
  @Override
  public Map<String, Object> deleteWebserviceForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteWebserviceForAdmin'");
  }

  /**
   * @기능 : 관리자용 웹 서비스 실행
   * @param svcId 실행할 서비스 ID
   * @return 실행 결과가 담긴 Map
   */
  @Override
  public Map<String, Object> executeWebserviceForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'executeWebserviceForAdmin'");
  }

  /**
   * @기능 : 관리자용 웹 서비스 실행 이력 조회
   * @param svcId 서비스 ID
   * @return 웹 서비스 실행 이력 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByWebserviceHistoryForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByWebserviceHistoryForAdmin'");
  }

}
