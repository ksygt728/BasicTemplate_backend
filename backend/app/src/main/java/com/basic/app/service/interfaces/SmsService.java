
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.SmsMReqDto;

/**
 * @파일명 : SmsService.java
 * @설명 : SMS 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface SmsService {

  /**
   * @기능 : 관리자용 SMS 전체 목록 조회 (페이징)
   * @param smsMReqDto SMS 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return SMS 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllSmsForAdmin(SmsMReqDto smsMReqDto,
      Pageable pageable);

  /**
   * @기능 : 관리자용 특정 SMS 상세 조회
   * @param smsId SMS ID
   * @return SMS 상세 정보가 담긴 Map
   */
  Map<String, Object> findBySmsForAdmin(String smsId);

  /**
   * @기능 : 관리자용 SMS 발송 이력 조회 (페이징)
   * @param smsId    SMS ID
   * @param pageable 페이징 정보
   * @return SMS 발송 이력 정보가 담긴 Map
   */
  Map<String, Object> findBySmsHistoryForAdmin(String smsId, Pageable pageable);

  /**
   * @기능 : 관리자용 SMS 등록
   * @param smsM SMS 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertSmsForAdmin(SmsMReqDto smsM);

  /**
   * @기능 : 관리자용 SMS 수정
   * @param smsM SMS 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateSmsForAdmin(SmsMReqDto smsM);

  /**
   * @기능 : 관리자용 SMS 삭제
   * @param smsId SMS ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteSmsForAdmin(String smsId);

  /**
   * @기능 : SMS 인증번호 발송
   * @param phoneNum 휴대폰 번호
   * @return 발송 결과 정보가 담긴 Map
   */
  Map<String, Object> smsAuth(String phoneNum);

  /**
   * @기능 : SMS 인증번호 검증
   * @param phoneNum 휴대폰 번호
   * @param smsCode  SMS 인증번호
   * @return 검증 결과 정보가 담긴 Map
   */
  Map<String, Object> smsAuthValidation(String phoneNum, String smsCode);
}
