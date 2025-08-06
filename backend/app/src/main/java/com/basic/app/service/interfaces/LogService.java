package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.basic.app.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;

public interface LogService {

  Map<String, Object> findAllAccessLogForAdmin();

  Map<String, Object> findByAccessLogForAdmin(String logId);

  Map<String, Object> findAllErrorLogForAdmin();

  Map<String, Object> findByErrorLogForAdmin(String errId);

  int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode,
      String validatorErrorMessage);

}
