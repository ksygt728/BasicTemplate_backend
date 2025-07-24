/**
 * @파일명   : NoticeReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import java.time.LocalDateTime;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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
public class NoticeReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "공지아이디는 필수입니다.")
  private String notId; // 공지아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "공지타입은 필수입니다.")
  private String notType; // 공지타입 (공지, 매뉴얼)

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "제목은 필수입니다.")
  private String title; // 제목

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "내용은 필수입니다.")
  private String content; // 내용

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "작성자는 필수입니다.")
  private String writor; // 작성자

  @Null(groups = { CreateGroup.class, UpdateGroup.class }, message = "작성일은 NULL이어야 합니다")
  private LocalDateTime writeDate; // 작성일

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자
}