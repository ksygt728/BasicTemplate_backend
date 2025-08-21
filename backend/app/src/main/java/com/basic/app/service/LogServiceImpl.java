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
import com.basic.app.repository.LogApiRepository;
import com.basic.app.repository.LogErrorRepository;
import com.basic.app.repository.jooqRepository.LogApiJooqRepository;
import com.basic.app.repository.jooqRepository.LogErrorJooqRepository;
import com.basic.app.service.interfaces.LogService;
import com.basic.app.util.UserRequestInfoManager;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

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

  @Override
  public Map<String, Object> findByErrorLogForAdmin(String errId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByErrorLogForAdmin'");
  }

  @Override
  public int insertApiLog(@RequestBody List<LogApi> logApi) {
    // Kafka에서 수신한 API 로그를 DB에 저장
    logApiRepository.saveAll(logApi);
    return 1;

  }

  @Override
  public int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode,
      String additionalMessage) {

    UserRequestInfoManager urm = new UserRequestInfoManager(request);

    // String errMsg = "[*** Response Error Message ***] : [errorCode : " +
    // errorCode.getCode() + "] - [message : "
    // + errorCode.getMessage() + additionalMessage + "] [*** Server Log ***] :
    // [Class : " + e.getClass()
    // + " - [Message : " + e.getMessage() + "]";

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

  private String getStackTraceAsString(Exception e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  @Override
  public Map<String, Object> findAllAccessLogForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllAccessLogForAdmin'");
  }

  @Override
  public Map<String, Object> findByAccessLogForAdmin(String logId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByAccessLogForAdmin'");
  }

}
