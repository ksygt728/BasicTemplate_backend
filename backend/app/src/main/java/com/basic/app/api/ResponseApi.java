package com.basic.app.api;

import com.basic.app.exception.ErrorCode;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ResponseApi<T> {

  private boolean success;

  private String errorCode;

  private String message;

  private T data;

  public static <T> ResponseApi<T> success(T data) {
    return ResponseApi.<T>builder()
        .success(true)
        .errorCode(null)
        .message("success")
        .data(data)
        .build();
  }

  public static <T> ResponseApi<T> fail(ErrorCode errorCode) {
    return ResponseApi.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(errorCode.getMessage())
        .data(null)
        .build();
  }

  public static <T> ResponseApi<T> fail(ErrorCode errorCode, String message) {
    return ResponseApi.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(errorCode.getMessage() + message)
        .data(null)
        .build();
  }

  public static <T> ResponseApi<T> fail(ErrorCode errorCode, String multiLangMessage, String message) {
    return ResponseApi.<T>builder()
        .success(false)
        .errorCode(errorCode.getCode())
        .message(multiLangMessage + message)
        .data(null)
        .build();
  }
}