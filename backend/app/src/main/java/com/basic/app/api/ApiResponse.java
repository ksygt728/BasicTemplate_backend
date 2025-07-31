/**
 * @파일명   : ApiResponse.java
 * @설명     : REST API 응답 래퍼 클래스
 * @작성자   : 김승연
 * @작성일   : 2025.07.24
 * @변경이력 :
 *   2025.07.24     김승연       최초 생성
 */
package com.basic.app.api;

import com.basic.app.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
  private boolean success;
  private String errorCode;
  private String message;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .success(true)
        .errorCode(null)
        .message("success")
        .data(data)
        .build();
  }

  // Enum으로 ErrorCode를 받는 메서드 추가
  public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
    return ApiResponse.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(errorCode.getMessage())
        .data(null)
        .build();
  }

  // getMessage 메서드에 추가 메시지를 포함할 수 있는 오버로딩
  public static <T> ApiResponse<T> fail(ErrorCode errorCode, String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(errorCode.getMessage() + message)
        .data(null)
        .build();
  }

}