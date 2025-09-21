package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @파일명 : SmsMReqDto.java
 * @설명 : SMS 마스터 요청 DTO
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
public class SmsMReqDto extends BaseReqDto {

  @Schema(description = "SMS아이디", example = "SMS001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "SMS아이디는 필수입니다.")
  private String smsId; // SMS 아이디

  @Schema(description = "언어타입", example = "ko")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어타입은 필수입니다.")
  private String langType; // 언어타입

  @Schema(description = "템플릿명", example = "회원가입인증")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "템플릿명은 필수입니다.")
  private String smsName; // 템플릿명

  @Schema(description = "SMS내용", example = "인증번호는 1234입니다.")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "SMS내용은 필수입니다.")
  private String text; // SMS내용

  @Schema(description = "설명", example = "SMS 설명")
  private String description; // 설명

}