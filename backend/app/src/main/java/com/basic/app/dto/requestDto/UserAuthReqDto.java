package com.basic.app.dto.requestDto;

import com.basic.app.entity.baseEntity.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : UserAuthReqDto.java
 * @설명 : 사용자 인증 요청 DTO
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthReqDto extends BaseEntity {
  // Validation 사용 안함
  @Schema(description = "사용자아이디", example = "user123")
  private String userId; // 사용자아이디

  @Schema(description = "리프레시토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String refreshToken; // 리프레시토큰

}