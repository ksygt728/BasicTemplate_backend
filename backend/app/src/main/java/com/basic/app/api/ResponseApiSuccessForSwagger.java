
/**
 * @파일명   : ResponseApiSuccessForSwagger.java
 * @설명     :  REST API 공통 실패 응답 래퍼 클래스(Swagger예시용일뿐 실제 코드에서 사용하지는 않음)
 * @작성자   : 김승연
 * @작성일   : 2025.08.18
 * @변경이력 :
 *   2025.08.18     김승연       최초 생성
 */

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
public class ResponseApiSuccessForSwagger<T> {

  @Schema(description = "요청 성공 여부", example = "true")
  private boolean success;

  @Schema(description = "에러 코드", example = "null")
  private String errorCode;

  @Schema(description = "응답 메시지", example = "success")
  private String message;

  @Schema(description = "실제 데이터", example = "실제데이터")
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
}