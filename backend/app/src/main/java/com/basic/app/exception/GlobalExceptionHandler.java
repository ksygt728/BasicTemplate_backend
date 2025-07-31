/**
 * @파일명   : GlobalExceptionHandler.java
 * @설명     : 전역 에러 핸들러
 *            1) 프론트엔드로 에러를 던질 때 사용 - 응답구조 ApiResponse로 통일
 *            2) 에러 로그 생성 및 로그테이블 적재
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.basic.app.api.ApiResponse;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /* Error code : 400 - 커스텀 에러 클래스 서비스단에서 동적으로 에러코드 전달(이상한 상황..) */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException e) {

    ErrorCode errorCode = e.getErrorCode();

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(errorCode));
  }

  /* Error code : 400 커스텀 에러 클래스 서비스단에서 동적으로 에러코드 전달(비즈니르로직으로 인한 validation) */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {

    ErrorCode errorCode = e.getErrorCode();

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(errorCode));
  }

  /* Error code : 400 (@Validated) - 원인 : 클라이언트 책임 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {

    ErrorCode errorCode = ErrorCode.VALIDATION_ERROR_CLIENT;

    List<FieldError> validatorErrors = e.getBindingResult().getFieldErrors();
    String validatorErrorMessage = validatorErrors.get(0).getDefaultMessage();

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage() + validatorErrorMessage,
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, validatorErrorMessage));
  }

  /* Error code : 400 (잘못된 요청 비즈로직 validation 실패 시) - 원인 : 게발자 실수 가능성 */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<?>> handleIllegalArgument(IllegalArgumentException e) {

    ErrorCode errorCode = ErrorCode.VALIDATION_ERROR_SERVER;

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(ErrorCode.VALIDATION_ERROR_SERVER));
  }

  // /* Error code : 401 (인증 실패) */
  // @ExceptionHandler(BadCredentialsException.class)
  // public ResponseEntity<ApiResponse<?>>
  // handleBadCredentials(BadCredentialsException e) {

  // log.error("인증실패(BadCredentials) : {}", e.getMessage());
  // return ResponseEntity
  // .status(HttpStatus.UNAUTHORIZED)
  // .body(ApiResponse.fail(ErrorCode.UNAUTHORIZED_FAILURE));
  // }

  // /* Error code : 403 (권한 없음) */
  // @ExceptionHandler(AccessDeniedException.class)
  // public ResponseEntity<ApiResponse<?>>
  // handleAccessDenied(AccessDeniedException e) {

  // log.error("권한 없음(AccessDenied) : {}", e.getMessage());

  // return ResponseEntity
  // .status(HttpStatus.FORBIDDEN)
  // .body(ApiResponse.fail(ErrorCode.ACCESS_DENIED));
  // }

  /* Error code : 404 (페이지를 찾을 수 없음) */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<?>> handleResponseStatus(ResponseStatusException e) {

    ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.fail(ErrorCode.PAGE_NOT_FOUND));
  }

  /* Error code : 404 (페이지를 찾을 수 없음) : 사용자가 URL입력을 잘못한 경우 */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResponse<?>> handleNoHandlerFound(NoHandlerFoundException e) {

    ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.fail(ErrorCode.PAGE_NOT_FOUND));
  }

  /* Error code : 500 (서버 내부 오류) */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception e) {

    ErrorCode errorCode = ErrorCode.SERVER_ERROR;

    log.warn(
        """

              [*** Response Error Message ***] : [errorCode : {}] - [message : {}]
              [*** Server Log ***] : [Class : {}] - [Message : {}]
              [*** Strace *** : {}]
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        e.getClass(),
        e.getMessage(),
        e.getStackTrace());

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.fail(ErrorCode.SERVER_ERROR));
  }

}