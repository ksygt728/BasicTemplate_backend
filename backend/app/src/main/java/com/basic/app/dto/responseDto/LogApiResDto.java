/**
 * @파일명   : LogApiResDto.java
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
public class LogApiResDto {

  private String logId; // 로그아이디

  private String userId; // 사용자 아이디

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime startDate; // 시작시간

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime endDate; // 종료시간

  private String ipAddr; // 아이피주소

  private String userAgent; // 브라우저 정보

  private String requestUri; // 요청 URI

  private String httpMethod; // 메소드

  private String requestBody; // 요청내용

  private String responseBody; // 응답내용

  private String statusCode; // STATUS_CODE

  private long execTime; // 실행시간

}