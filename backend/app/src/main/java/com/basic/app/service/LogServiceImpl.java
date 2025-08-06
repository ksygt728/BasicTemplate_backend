package com.basic.app.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.basic.app.entity.LogError;
import com.basic.app.exception.ErrorCode;
import com.basic.app.repository.LogErrorRepository;
import com.basic.app.service.interfaces.LogService;
import com.basic.app.util.UserRequestInfoManager;

import jakarta.servlet.http.HttpServletRequest;

@Transactional
@Service
public class LogServiceImpl implements LogService {

  @Autowired
  private LogErrorRepository logErrorRepository;

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

  @Override
  public Map<String, Object> findAllErrorLogForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllErrorLogForAdmin'");
  }

  @Override
  public Map<String, Object> findByErrorLogForAdmin(String errId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByErrorLogForAdmin'");
  }

  @Override
  public int insertErrorLog(Exception e, HttpServletRequest request, ErrorCode errorCode,
      String validatorErrorMessage) {

    UserRequestInfoManager urm = new UserRequestInfoManager(request);

    String errMsg = "[*** Response Error Message ***] : [errorCode : " + errorCode.getCode() + "] - [message : "
        + errorCode.getMessage() + validatorErrorMessage + "] [*** Server Log ***] : [Class : " + e.getClass()
        + " - [Message : " + e.getMessage() + "]";

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

}
