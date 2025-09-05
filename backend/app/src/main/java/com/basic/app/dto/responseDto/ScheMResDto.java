package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : ScheMResDto.java
 * @설명 : 스케줄 마스터 정보 응답 데이터 전송 객체
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
public class ScheMResDto {
  private String scheId; // 스케줄아이디

  private String scheName; // 스케줄명

  private String description; // 설명

  private String scheGroup; // 스케줄러 그뤂명

  private String className; // 클래스명

  private String methodName; // 메소드명

  private String triggerName; // 트리거명

  private String cronExp; // CRON식

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime lastExecTime; // 마지막실행시간

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime nextExecTime; // 마지막실행시간

  private String useYn; // 사용여부 (Y,N)

}