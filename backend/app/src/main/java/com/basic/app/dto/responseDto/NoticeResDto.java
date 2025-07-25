/**
 * @파일명   : NoticeResDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;

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
public class NoticeResDto {

  private String notId; // 공지아이디

  private String notType; // 공지타입 (공지, 매뉴얼)

  private String title; // 제목

  private String content; // 내용

  private String writor; // 작성자

  private LocalDateTime writeDate; // 작성일

  private String sts; // 시스템 상태 (C, D)

  private String createUser; // 생성자

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate; // 생성일

  private String updateUser; // 수정자

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp; // 수정일

}