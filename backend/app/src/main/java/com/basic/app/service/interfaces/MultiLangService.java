
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.MulLangReqDto;

/**
 * @파일명 : MultiLangService.java
 * @설명 : 다국어 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */

public interface MultiLangService {

  /**
   * @기능 : 관리자용 다국어 전체 목록 조회 (페이징)
   * @param mulLangReqDto 다국어 검색 조건 DTO
   * @param pageable      페이징 정보
   * @return 다국어 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllMulLangForAdmin(MulLangReqDto mulLangReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 다국어 상세 조회
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 다국어 상세 정보가 담긴 Map
   */
  Map<String, Object> findByMulLangForAdmin(String langGubun, String langCd);

  /**
   * @기능 : 관리자용 다국어 등록
   * @param mulLang 다국어 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertMulLangForAdmin(MulLangReqDto mulLang);

  /**
   * @기능 : 관리자용 다국어 수정
   * @param mulLang 다국어 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateMulLangForAdmin(MulLangReqDto mulLang);

  /**
   * @기능 : 관리자용 다국어 삭제
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteMulLangForAdmin(String langGubun, String langCd);

  /**
   * @기능 : 관리자용 다국어 상세 삭제
   * @param langType  언어 타입
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteMulLangDetailForAdmin(String langType, String langGubun, String langCd);
}
