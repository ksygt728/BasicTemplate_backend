/**
 * @파일명   : LogActResDto.java
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
public class LogActResDto {

  private String logId; // 로그아이디

  private String userId; // 사용자 아이디

  private String ipAddr; // 아이피주소

  private String pageUrl; // 페이지URL

  private String actionType; // 액션타입

  private String actionTypeDetail; // 액션 내용

}