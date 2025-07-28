/**
 * @파일명   : BbsResDto.java
 * @설명     : 게시판 정보 응답 데이터 전송 객체
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class BbsResDto {

  private String bbsId; // 게시판아이디

  private String bbsType; // 게시판타입

  private String title; // 제목

  private String content; // 내용

  // Bbs - User (N:1) [Onwer]
  private String writor; // 작성자

  private LocalDateTime writeDate; // 작성일

  // Bbs - BbsComment (1:N)
  private List<BbsCommentResDto> bbsComments = new ArrayList<BbsCommentResDto>(); // 게시글 댓글 리스트

}