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

/**
 * @파일명 : BbsCommentReqDto.java
 * @설명 : 게시판 댓글 요청 DTO
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
public class BbsCommentReqDto extends BaseReqDto {

  @Schema(description = "댓글아이디", example = "CMT001")
  @NotBlank(groups = UpdateGroup.class, message = "댓글아이디는 필수입니다.")
  private String commentId; // 댓글아이디

  // BbsComemnt - Bbs (N:1) [Onwer]
  @Schema(description = "게시판아이디", example = "BBS001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "게시판아이디는 필수입니다.")
  private String bbsId; // 게시판아이디

  @Schema(description = "댓글 내용", example = "좋은 글 감사합니다.")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "댓글을 입력하세요.")
  private String commentContent; // 내용

  @Schema(description = "작성자", example = "user123")
  @NotBlank(groups = { UpdateGroup.class }, message = "작성자는 필수 입니다.") // insert할떄는 로그인한 사용자로 확인
  private String writor; // 작성자

}