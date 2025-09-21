package com.basic.app.api;

import com.basic.app.exception.ErrorCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : ResponseApi.java
 * @설명 : REST API 공통 응답 래퍼 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi<T> {

  private boolean success;

  private String errorCode;

  private String message;

  private T data;

  /**
   * @기능 : 성공 응답 생성
   * @param data 응답 데이터
   * @return 성공 응답 객체
   */
  public static <T> ResponseApi<T> success(T data) {
    return ResponseApi.<T>builder()
        .success(true)
        .errorCode(null)
        .message("success")
        .data(data)
        .build();
  }

  /**
   * @기능 : 실패 응답 생성
   * @param errorCode 에러 코드
   * @return 실패 응답 객체
   */
  public static <T> ResponseApi<T> fail(ErrorCode errorCode) {
    return ResponseApi.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(errorCode.getMessage())
        .data(null)
        .build();
  }

  /**
   * @기능 : 실패 응답 생성 (추가 메시지 포함)
   * @param errorCode 에러 코드
   * @param message   추가 메시지
   * @return 실패 응답 객체
   */
  public static <T> ResponseApi<T> fail(ErrorCode errorCode, String message) {
    return ResponseApi.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(errorCode.getMessage() + message)
        .data(null)
        .build();
  }

  /**
   * @기능 : 실패 응답 생성 (다국어 메시지 및 추가 메시지 포함)
   * @param errorCode        에러 코드
   * @param multiLangMessage 다국어 메시지
   * @param message          추가 메시지
   * @return 실패 응답 객체
   */
  public static <T> ResponseApi<T> fail(ErrorCode errorCode, String multiLangMessage, String message) {
    return ResponseApi.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(multiLangMessage + message)
        .data(null)
        .build();
  }
}