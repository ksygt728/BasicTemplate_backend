
package com.basic.app.api;

import com.basic.app.exception.ErrorCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : ResponseApiFailForSwagger.java
 * @설명 : REST API 공통 실패 응답 래퍼 클래스 (Swagger 예시용일뿐 실제 코드에서 사용하지는 않음)
 * @작성자 : 김승연
 * @작성일 : 2025.08.18
 * @변경이력 :
 *       2025.08.18 김승연 최초 생성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApiFailForSwagger<T> {

  @Schema(description = "요청 성공 여부", example = "false")
  private boolean success;

  @Schema(description = "에러 코드", example = "각 상황에 맞는 에러코드")
  private String errorCode;

  @Schema(description = "응답 메시지", example = "각 상황에 맞는 에러 메세지")
  private String message;

  @Schema(description = "실제 데이터", example = "null")
  private T data;

}