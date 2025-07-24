/**
 * @파일명   : ApiResponse.java
 * @설명     : REST API 응답 래퍼 클래스
 * @작성자   : 김승연
 * @작성일   : 2025.07.24
 * @변경이력 :
 *   2025.07.24     김승연       최초 생성
 */
package com.basic.app.custom;

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
  private String message;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .success(true)
        .message("success")
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> fail(String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .message(message)
        .data(null)
        .build();
  }

}