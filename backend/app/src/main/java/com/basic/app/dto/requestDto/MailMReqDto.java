/**
 * @파일명   : MailMReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailMReqDto extends BaseReqDto {

  @Schema(description = "메일아이디", example = "MAIL001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메일아이디는 필수입니다.")
  private String mailId; // 메일아이디

  @Schema(description = "언어타입", example = "ko")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어타입은 필수입니다.")
  private String langType; // 언어타입

  @Schema(description = "메일명", example = "회원가입메일")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메일명은 필수입니다.")
  private String mailName; // 메일명

  @Schema(description = "제목", example = "메일 제목")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "제목은 필수입니다.")
  private String title; // 제목

  @Schema(description = "내용", example = "메일 내용입니다.")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "내용은 필수입니다.")
  private String content; // 내용

  @Schema(description = "설명", example = "메일 설명")
  private String description; // 설명

}