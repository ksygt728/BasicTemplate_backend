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
 * @파일명 : LogErrorResDto.java
 * @설명 : 에러 로그 정보 응답 데이터 전송 객체
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 *       2025.12.16 김승연 createDate 조회조건 추가
 */
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

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private String createDate; // 생성일시

}