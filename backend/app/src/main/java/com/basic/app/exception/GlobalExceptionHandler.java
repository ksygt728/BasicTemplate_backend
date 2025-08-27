/**
 * @파일명   : GlobalExceptionHandler.java
 * @설명     : 전역 에러 핸들러
 *            1) 프론트엔드로 에러를 던질 때 사용 - 응답구조 ResponseApi로 통일
 *            2) 에러 로그 생성 및 로그테이블 적재
 * 
 *         [커스텀 에러 Exception 클래스 구분]
 *           1) [하] BusinessException : 비즈니스 로직에서 발생하는 예외로 업무과 관련된 예외.
 *              - 비즈니스예외를 처리하기 위해, 예외상황이 아니지만 일부러 예외를 만들어 던지는 경우임
 *              - 예외상황이 명확함
 *           2) [중] ClientActionException : 비즈니스예외는 아니지만 명확한 클라이언트의 잘못된 동작으로 인해 예외가 발생하는 경우
 *           3) [상] SystemErrorException : try-catch 등 예외가 발생하는 구간이 있지만 어떤 예외가 발생할지 애매한경우(예측불가) 각종 에러가 발생할 수 있는 부분 등 예외함.
 *               - 원래는 예측되지 않은 예외는 '관리자에게 문의하세요'로 던졌지만 에러발생원인을 파악하기 위해 중간단계로써 추가 
 *               - ErrorCode와 파라미터를 실어 같이 던질 수 있음 
 *               - 에러확인을 위해 Exception받아 실제 예외를 던지는 구조
 *               - 여기서 예외가 잡혔다면 명확한 처리를 위해 별도로 실제 예외를 던져야함(BusinessException 또는 ClientActionException)
 *           4) [상] NotFoundException : 주로 클라이언트의 동작과 서버의 동작이 불일치 할 경우
 *               - 해당 예외가 발생한 경우는 뭔가 개발이 잘못된 경우임
 * *             - e.g. 수정을 하는데 수정할 데이터가 없는 경우, 삭제를 하는데 삭제할 데이터가 없는 경우
 * 
 * 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 *  2025.07.24     김승연       커스텀 에러 클래스 추가
 */

package com.basic.app.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.basic.app.api.ResponseApi;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.ClientActionException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.exception.customException.SystemErrorException;
import com.basic.app.service.interfaces.LogService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Autowired
  private LogService logService;

  /*
   * ====================================================================
   * =====================[CUSTOM EXCEPTION HANDLER]=====================
   * ====================================================================
   */

  /* Error code : 400 커스텀 에러 클래스 서비스단에서 동적으로 에러코드 전달(비즈니르로직으로 인한 validation) */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ResponseApi<?>> handleBusinessException(BusinessException e, HttpServletRequest request) {

    ErrorCode errorCode = e.getErrorCode();
    String additionalMessage = e.getAdditionalMessage() == null ? "" : e.getAdditionalMessage();

    showErrorLogFormat(e, errorCode, additionalMessage);

    try {
      logService.insertErrorLog(e, request, errorCode, additionalMessage);
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseApi.fail(errorCode, additionalMessage));
  }

  /* Error code : 400 클라이언트의 잘못된 요청으로 인해 발생하는 예외를 처리하기 위한 커스텀 예외 클래스 */
  @ExceptionHandler(ClientActionException.class)
  public ResponseEntity<ResponseApi<?>> handleClientActionException(ClientActionException e,
      HttpServletRequest request) {

    ErrorCode errorCode = e.getErrorCode();
    String additionalMessage = e.getAdditionalMessage() == null ? "" : e.getAdditionalMessage();

    showErrorLogFormat(e, errorCode, additionalMessage);

    try {
      logService.insertErrorLog(e, request, errorCode, additionalMessage);
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseApi.fail(errorCode, additionalMessage));
  }

  /* Error code : 400 - try-catch나 관리자가 직접 에러메세지를보고 판단해야하는 경우 */
  @ExceptionHandler(SystemErrorException.class)
  public ResponseEntity<ResponseApi<?>> handleSystemErrorException(SystemErrorException e, HttpServletRequest request) {

    ErrorCode errorCode = e.getErrorCode();
    String additionalMessage = e.getAdditionalMessage() == null ? "" : e.getAdditionalMessage();

    showErrorLogFormat(e.getE(), errorCode, additionalMessage);

    try {
      logService.insertErrorLog(e.getE(), request, errorCode, additionalMessage);
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseApi.fail(errorCode, additionalMessage));
  }

  /* Error code : 400 - 커스텀 에러 클래스 서비스단에서 동적으로 에러코드 전달(이상한 상황..) */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ResponseApi<?>> handleNotFoundException(NotFoundException e, HttpServletRequest request) {

    ErrorCode errorCode = e.getErrorCode();
    String additionalMessage = e.getAdditionalMessage() == null ? "" : e.getAdditionalMessage();

    showErrorLogFormat(e, errorCode, additionalMessage);

    try {
      logService.insertErrorLog(e, request, errorCode, additionalMessage);
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseApi.fail(errorCode, additionalMessage));
  }

  /*
   * ====================================================================
   * =====================[NORMAL EXCEPTION HANDLER]=====================
   * ====================================================================
   */

  /* Error code : 400 (@Validated) - 원인 : 클라이언트 책임 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseApi<?>> handleValidationException(MethodArgumentNotValidException e,
      HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.VALIDATION_ERROR_CLIENT;

    List<FieldError> validatorErrors = e.getBindingResult().getFieldErrors();
    String validatorErrorMessage = validatorErrors.get(0).getDefaultMessage();

    showErrorLogFormat(e, errorCode, validatorErrorMessage);

    try {
      logService.insertErrorLog(e, request, errorCode, validatorErrorMessage);
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, validatorErrorMessage));
  }

  /* Error code : 400 (Json Body 오류) - 원인 : 클라이언트 책임 */
  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity<ResponseApi<?>> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e,
      HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.VALIDATION_ERROR_JSON;

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_JSON, ""));
  }

  /* Error code : 400 (잘못된 요청 비즈로직 validation 실패 시) - 원인 : 게발자 실수 가능성 */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseApi<?>> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.VALIDATION_ERROR_SERVER;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseApi.fail(ErrorCode.VALIDATION_ERROR_SERVER));
  }

  /* Error code : 401 (로그인 인증 실패시) */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseApi<?>> handleUsernameNotFound(BadCredentialsException e, HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.UNAUTHORIZED_FAILURE;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ResponseApi.fail(ErrorCode.UNAUTHORIZED_FAILURE));
  }

  /* Error code : 403 (권한이 없을경우 @PreAuthorize) */
  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ResponseApi<?>> handleAuthorizationDenied(AuthorizationDeniedException e,
      HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ResponseApi.fail(ErrorCode.ACCESS_DENIED));
  }

  /* Error code : 404 (페이지를 찾을 수 없음) */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ResponseApi<?>> handleResponseStatus(ResponseStatusException e, HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND));
  }

  /* Error code : 404 (페이지를 찾을 수 없음) */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ResponseApi<?>> handleNoResourceFoundException(NoResourceFoundException e,
      HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND));
  }

  /* Error code : 404 (페이지를 찾을 수 없음) */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ResponseApi<?>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e, HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND));
  }

  /* Error code : 404 (페이지를 찾을 수 없음) : 사용자가 URL입력을 잘못한 경우 */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ResponseApi<?>> handleNoHandlerFound(NoHandlerFoundException e, HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND));
  }

  /* Error code : 500 (서버 내부 오류) */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseApi<?>> handleException(Exception e, HttpServletRequest request) {

    ErrorCode errorCode = ErrorCode.SERVER_ERROR;

    showErrorLogFormat(e, errorCode);

    try {
      logService.insertErrorLog(e, request, errorCode, "");
    } catch (Exception ex) {
      log.error("CBSK : GlobalExceptionHandler 로그 저장 중 오류가 발생했습니다. ERROR내용 : ");
      ex.printStackTrace();
    }

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseApi.fail(ErrorCode.SERVER_ERROR));
  }

  // 로그 출력 형식(default)
  public void showErrorLogFormat(Exception e, ErrorCode errorCode) {
    log.warn(
        """

              [*** Response Error Message ***]
              - ErrorCode : {}
              - Message : {}
              [*** Server Log ***]
              - Class : {}
              - Message : {}
              [*** Strace ***]
              {}
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());
  }

  // 로그 출력 형식(VALIDATION_ERROR_CLIENT 에 추가 메시지 전달(예: @Validated 어노테이션에서 발생한 에러
  // 메시지))
  public void showErrorLogFormat(Exception e, ErrorCode errorCode, String additionalMessage) {
    log.warn(
        """

              [*** Response Error Message ***]
              - ErrorCode : {}
              - Message : {}
              [*** Server Log ***]
              - Class : {}
              - Message : {}
              [*** Strace ***]
              {}
            """,
        errorCode.getCode(),
        errorCode.getMessage() + additionalMessage,
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());
  }

}