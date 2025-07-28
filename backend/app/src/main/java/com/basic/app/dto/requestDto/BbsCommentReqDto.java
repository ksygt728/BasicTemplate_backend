/**
 * @파일명   : BbsCommentReqDto.java
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
public class BbsCommentReqDto {

  @NotBlank(groups = UpdateGroup.class, message = "댓글아이디는 필수입니다.")
  private String commentId; // 댓글아이디

  // BbsComemnt - Bbs (N:1) [Onwer]
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "게시판아이디는 필수입니다.")
  private String bbsId; // 게시판아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "댓글을 입력하세요.")
  private String content; // 내용

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "작성자는 필수 입니다.")
  private String writor; // 작성자

  @Null(groups = { CreateGroup.class, UpdateGroup.class }, message = "작성일은 Null이어야 합니다.")
  private LocalDateTime writeDate; // 작성일

}