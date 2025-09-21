package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : MailMResDto.java
 * @설명 : 메일 마스터 정보 응답 데이터 전송 객체
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
public class MailMResDto {
  private String mailId; // 메일아이디

  private String langType; // 언어타입

  private String mailName; // 메일명

  private String title; // 제목

  private String content; // 내용

  private String description; // 설명

}