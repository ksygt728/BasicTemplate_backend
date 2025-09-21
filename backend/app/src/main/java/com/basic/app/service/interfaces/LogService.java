
package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.LogApiReqDto;
import com.basic.app.dto.requestDto.LogErrorReqDto;
import com.basic.app.entity.LogApi;
import com.basic.app.exception.ErrorCode;
/**
 * @파일명   : LogService.java
 * @설명     : 로그 관련 서비스 인터페이스
 * @작성자   : 김승연
 * @작성일   : 2025.08.6
 * @변경이력 :
 *   2025.08.6     김승연       최초 생성
 */
import jakarta.servlet.http.HttpServletRequest;

public interface LogService {

  /**
   * @기능 : 관리자용 접근 로그 전체 목록 조회
   * @return 접근 로그 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllAccessLogForAdmin();

  /**
   * @기능 : 관리자용 특정 접근 로그 상세 조회
   * @param logId 로그 ID
   * @return 접근 로그 상세 정보가 담긴 Map
   */
  Map<String, Object> findByAccessLogForAdmin(String logId);

  /**
   * @기능 : 관리자용 에러 로그 목록 조회 (페이징)
   * @param logErrorReqDto 에러 로그 검색 조건 DTO
   * @param pageable       페이징 정보
   * @return 에러 로그 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllErrorLogForAdmin(LogErrorReqDto logErrorReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 에러 로그 상세 조회
   * @param errId 에러 ID
   * @return 에러 로그 상세 정보가 담긴 Map
   */
  Map<String, Object> findByErrorLogForAdmin(String errId);

  /**
   * @기능 : 에러 로그 등록 (기본)
   * @param e                 발생한 예외
   * @param request           HTTP 요청 객체
   * @param errorCode         에러 코드
   * @param additionalMessage 추가 메시지
   * @return 등록 결과 (영향받은 행 수)
   */
  int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode,
      String additionalMessage);

  /**
   * @기능 : 에러 로그 등록 (메시지 포함)
   * @param e                 발생한 예외
   * @param request           HTTP 요청 객체
   * @param errorCode         에러 코드
   * @param message           메시지
   * @param additionalMessage 추가 메시지
   * @return 등록 결과 (영향받은 행 수)
   */
  int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode, String message,
      String additionalMessage);

  /**
   * @기능 : API 로그 등록
   * @param logApi API 로그 목록
   * @return 등록 결과 (영향받은 행 수)
   */
  int insertApiLog(List<LogApi> logApi);

  /**
   * @기능 : 관리자용 API 로그 목록 조회 (페이징)
   * @param logApiReqDto API 로그 검색 조건 DTO
   * @param pageable     페이징 정보
   * @return API 로그 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllApiLogForAdmin(LogApiReqDto logApiReqDto, Pageable pageable);

}
