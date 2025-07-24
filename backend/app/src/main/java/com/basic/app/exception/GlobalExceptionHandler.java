/**
 * @파일명   : GlobalExceptionHandler.java
 * @설명     : 전역 에러 핸들러
 *            1) 프론트엔드로 에러를 던질 때 사용 - 응답구조 ApiResponse로 통일
 *            2) 에러 로그 생성 및 로그테이블 적재
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */

package com.basic.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.basic.app.custom.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /* Error를 동적으로 던질 때 사용 */
  // @ExceptionHandler(ResponseStatusException.class)
  // public ResponseEntity<ApiResponse<?>>
  // handleResponseStatusException(ResponseStatusException e) {
  // return ResponseEntity
  // .status(e.getStatusCode())
  // .body(ApiResponse.fail(e.getReason()));
  // }

  /* Error code : 400 (@Validated) */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
    String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    log.warn("잘못된 요청(Validated) : {}", errorMessage);

    return ResponseEntity
        .badRequest()
        .body(ApiResponse.fail("Validation : " + errorMessage));
  }

  /* Error code : 400 (잘못된 요청 비즈로직 validation 실패 시) */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<?>> handleIllegalArgument(IllegalArgumentException e) {
    log.warn("잘못된 요청 : {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(e.getMessage()));
  }

  /* Error code : 401 (인증 실패) */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<?>> handleBadCredentials(BadCredentialsException e) {
    log.error("인증실패(401) : {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ApiResponse.fail(e.getMessage()));
  }

  /* Error code : 403 (권한 없음) */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponse<?>> handleAccessDenied(AccessDeniedException e) {
    log.error("권한 없음(403) : {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ApiResponse.fail(e.getMessage()));
  }

  /* Error code : 404 (페이지를 찾을 수 없음) */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<?>> handleResponseStatus(ResponseStatusException e) {
    log.error("페이지 없음(404) : {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.fail(e.getMessage()));
  }

  /* Error code : 500 (서버 내부 오류) */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
    log.error("서버 에러(500) : {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.fail("서버 오류가 발생했습니다. 관리자에게 문의하세요."));
  }

}