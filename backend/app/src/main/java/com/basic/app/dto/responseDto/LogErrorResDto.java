/**
 * @파일명   : LogErrorResDto.java
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
public class LogErrorResDto {

  private String errId; // 로그아이디

  private String userId; // 사용자 아이디

  private String ipAddr; // 아이피주소

  private String userAgent; // 브라우저 정보

  private String requestUri; // 요청 URI

  private String httpMethod; // 메소드

  private String errMsg; // 에러내용

  private String errStack; // 에러내용상세

  private String sts; // 시스템 상태 (C, D)

  private String createUser; // 생성자

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate; // 생성일

  private String updateUser; // 수정자

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp; // 수정일
}