/**
 * @파일명   : SmsMReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsMReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "SMS아이디는 필수입니다.")
  private String smsId; // SMS 아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어타입은 필수입니다.")
  private String langType; // 언어타입

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "템플릿명은 필수입니다.")
  private String smsName; // 템플릿명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "SMS내용은 필수입니다.")
  private String text; // SMS내용

  private String description; // 설명

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자
}