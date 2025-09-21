package com.basic.app.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.LogApiReqDto;
import com.basic.app.dto.requestDto.LogErrorReqDto;
import com.basic.app.dto.responseDto.LogApiResDto;
import com.basic.app.dto.responseDto.LogErrorResDto;
import com.basic.app.entity.LogApi;
import com.basic.app.entity.LogError;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.repository.LogApiRepository;
import com.basic.app.repository.LogErrorRepository;
import com.basic.app.repository.jooqRepository.LogApiJooqRepository;
import com.basic.app.repository.jooqRepository.LogErrorJooqRepository;
import com.basic.app.service.interfaces.LogService;
import com.basic.app.util.UserRequestInfoManager;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @파일명 : LogServiceImpl.java
 * @설명 : 로그 관련 서비스 구현체 (API 로그, 에러 로그, 접근 로그)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class LogServiceImpl implements LogService {

  @Autowired
  private LogApiRepository logApiRepository;

  @Autowired
  private LogApiJooqRepository logApiJooqRepository;

  @Autowired
  private LogErrorRepository logErrorRepository;

  @Autowired
  private LogErrorJooqRepository logErrorJooqRepository;

  LogServiceImpl(LogApiRepository logApiRepository) {
    this.logApiRepository = logApiRepository;
  }

  /**
   * @기능 : 관리자용 API 로그 전체 목록 조회 (페이징)
   * @param logApiReqDto API 로그 검색 조건 DTO
   * @param pageable     페이징 정보
   * @return API 로그 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllApiLogForAdmin(LogApiReqDto logApiReqDto, Pageable pageable) {
    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 데이터 조회
    Page<LogApiResDto> logApiDtoList = logApiJooqRepository.findAllLogApiWithConditions(logApiReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<LogApiResDto> pagedLogApiDtoList = ModelMapperUtils.map(logApiDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedLogApiDtoList);

    return data;
  }

  /**
   * @기능 : 관리자용 에러 로그 전체 목록 조회 (페이징)
   * @param logErrorReqDto 에러 로그 검색 조건 DTO
   * @param pageable       페이징 정보
   * @return 에러 로그 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllErrorLogForAdmin(LogErrorReqDto logErrorReqDto, Pageable pageable) {
    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 데이터 조회
    Page<LogErrorResDto> logErrorDtoList = logErrorJooqRepository.findAllLogErrorWithConditions(logErrorReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<LogErrorResDto> pagedLogErrorDtoList = ModelMapperUtils.map(logErrorDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedLogErrorDtoList);

    return data;
  }

  /**
   * @기능 : 관리자용 특정 에러 로그 상세 조회
   * @param errId 에러 ID
   * @return 에러 로그 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByErrorLogForAdmin(String errId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByErrorLogForAdmin'");
  }

  /**
   * @기능 : API 로그 데이터 일괄 저장
   * @param logApi API 로그 목록
   * @return 저장 성공 여부 (1: 성공)
   */
  @Override
  public int insertApiLog(@RequestBody List<LogApi> logApi) {
    // Kafka에서 수신한 API 로그를 DB에 저장
    logApiRepository.saveAll(logApi);
    return 1;

  }

  /**
   * @기능 : 에러 로그 데이터 저장
   * @param e                 발생한 예외
   * @param request           HTTP 요청 정보
   * @param errorCode         에러 코드
   * @param additionalMessage 추가 메시지
   * @return 저장 성공 여부 (1: 성공)
   */
  @Override
  public int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode,
      String additionalMessage) {

    UserRequestInfoManager urm = new UserRequestInfoManager(request);

    String errMsg = """
          [*** Response Error Message ***]
          - ErrorCode : %s
          - Message : %s
          [*** Server Log ***]
          - Class : %s
          - Message : %s
        """.formatted(
        errorCode.getCode(),
        errorCode.getMessage() + additionalMessage,
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    String errStack = getStackTraceAsString(e);

    LogError logError = LogError.builder()
        .userId(urm.getUserId())
        .ipAddr(urm.getIpAddr())
        .userAgent(urm.getUserAgent())
        .requestUri(urm.getRequestUri())
        .httpMethod(urm.getHttpMethod())
        .errMsg(errMsg) // application log와 동일하게 저장
        .errStack(errStack)
        .build();

    logErrorRepository.save(logError);
    return 1;

  }

  /**
   * @기능 : 에러 로그 데이터 저장 (커스텀 메시지 포함)
   * @param e                 발생한 예외
   * @param request           HTTP 요청 정보
   * @param errorCode         에러 코드
   * @param message           커스텀 메시지
   * @param additionalMessage 추가 메시지
   * @return 저장 성공 여부 (1: 성공)
   */
  @Override
  public int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode, String message,
      String additionalMessage) {

    UserRequestInfoManager urm = new UserRequestInfoManager(request);

    String errMsg = """
          [*** Response Error Message ***]
          - ErrorCode : %s
          - Message : %s
          [*** Server Log ***]
          - Class : %s
          - Message : %s
        """.formatted(
        errorCode.getCode(),
        message + additionalMessage,
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    String errStack = getStackTraceAsString(e);

    LogError logError = LogError.builder()
        .userId(urm.getUserId())
        .ipAddr(urm.getIpAddr())
        .userAgent(urm.getUserAgent())
        .requestUri(urm.getRequestUri())
        .httpMethod(urm.getHttpMethod())
        .errMsg(errMsg) // application log와 동일하게 저장
        .errStack(errStack)
        .build();

    logErrorRepository.save(logError);
    return 1;
  }

  /**
   * @기능 : 예외의 스택 트레이스를 문자열로 변환
   * @param e 변환할 예외
   * @return 스택 트레이스 문자열
   */
  private String getStackTraceAsString(Exception e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  /**
   * @기능 : 관리자용 접근 로그 전체 목록 조회
   * @return 접근 로그 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllAccessLogForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllAccessLogForAdmin'");
  }

  /**
   * @기능 : 관리자용 특정 접근 로그 상세 조회
   * @param logId 로그 ID
   * @return 접근 로그 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByAccessLogForAdmin(String logId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByAccessLogForAdmin'");
  }

}
