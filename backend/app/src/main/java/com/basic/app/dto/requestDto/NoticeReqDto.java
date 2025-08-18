/**
 * @파일명   : NoticeReqDto.java
 * @설명     : 공지사항 관리를 위한 요청 데이터 전송 객체
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import java.time.LocalDateTime;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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
public class NoticeReqDto extends BaseReqDto {

  @Schema(description = "공지아이디", example = "NOT001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "공지아이디는 필수입니다.")
  private String notId; // 공지아이디

  @Schema(description = "공지타입", example = "공지")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "공지타입은 필수입니다.")
  private String notType; // 공지타입 (공지, 매뉴얼)

  @Schema(description = "제목", example = "공지사항 제목")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "제목은 필수입니다.")
  private String title; // 제목

  @Schema(description = "내용", example = "공지사항 내용입니다.")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "내용은 필수입니다.")
  private String content; // 내용

  @Schema(description = "작성자", example = "user123")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "작성자는 필수입니다.")
  private String writor; // 작성자

  @Schema(description = "작성일", example = "* 시스템에서 자동으로 주기때문에 별도 기입 불필요")
  @Null(groups = { CreateGroup.class, UpdateGroup.class }, message = "작성일은 NULL이어야 합니다")
  private LocalDateTime writeDate; // 작성일

}