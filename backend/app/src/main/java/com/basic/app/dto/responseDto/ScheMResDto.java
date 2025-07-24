/**
 * @파일명   : ScheMResDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;

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
public class ScheMResDto {
  private String scheId; // 스케줄아이디

  private String scheName; // 스케줄명

  private String description; // 설명

  private String cronExp; // CRON식

  private LocalDateTime lastExecTime; // 마지막실행시간

  private String useYn; // 사용여부 (Y,N)

  private String sts; // 시스템 상태 (C, D)

  private String createUser; // 생성자

  private LocalDateTime createDate; // 생성일

  private String updateUser; // 수정자

  private LocalDateTime timestamp; // 수정일
}