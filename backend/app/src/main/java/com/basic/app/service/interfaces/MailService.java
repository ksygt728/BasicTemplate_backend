
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.MailMReqDto;

/**
 * @파일명 : MailService.java
 * @설명 : 메일 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface MailService {

  /**
   * @기능 : 메일 발송 테스트 1
   * @throws Exception 예외 발생시
   */
  public void mailSendTest1() throws Exception;

  /**
   * @기능 : 메일 발송 테스트 2
   * @throws Exception 예외 발생시
   */
  public void mailSendTest2() throws Exception;

  /**
   * @기능 : 관리자용 메일 전체 목록 조회 (페이징)
   * @param mailMReqDto 메일 검색 조건 DTO
   * @param pageable    페이징 정보
   * @return 메일 목록 정보가 담긴 Map
   * @throws Exception 예외 발생시
   */
  Map<String, Object> findAllMailForAdmin(MailMReqDto mailMReqDto, Pageable pageable) throws Exception;

  /**
   * @기능 : 관리자용 특정 메일 상세 조회
   * @param mailId 메일 ID
   * @return 메일 상세 정보가 담긴 Map
   */
  Map<String, Object> findByMailForAdmin(String mailId);

  /**
   * @기능 : 관리자용 메일 발송 이력 조회 (페이징)
   * @param mailId   메일 ID
   * @param pageable 페이징 정보
   * @return 메일 발송 이력 정보가 담긴 Map
   */
  Map<String, Object> findByMailHistoryForAdmin(String mailId, Pageable pageable);

  /**
   * @기능 : 관리자용 메일 등록
   * @param mailM 메일 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertMailForAdmin(MailMReqDto mailM);

  /**
   * @기능 : 관리자용 메일 수정
   * @param mailM 메일 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateMailForAdmin(MailMReqDto mailM);

  /**
   * @기능 : 관리자용 메일 삭제
   * @param mailId 메일 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteMailForAdmin(String mailId);

}
