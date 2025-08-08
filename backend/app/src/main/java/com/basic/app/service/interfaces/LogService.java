package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.LogErrorReqDto;
import com.basic.app.entity.LogApi;
import com.basic.app.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;

public interface LogService {

  Map<String, Object> findAllAccessLogForAdmin();

  Map<String, Object> findByAccessLogForAdmin(String logId);

  Map<String, Object> findAllErrorLogForAdmin(LogErrorReqDto logErrorReqDto, Pageable pageable);

  Map<String, Object> findByErrorLogForAdmin(String errId);

  int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode,
      String validatorErrorMessage);

  int insertApiLog(List<LogApi> logApi);

}
