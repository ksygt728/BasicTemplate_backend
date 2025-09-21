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
 * @파일명 : ScheHResDto.java
 * @설명 : 스케줄 이력 정보 응답 데이터 전송 객체
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
public class ScheHResDto {

  private String logId; // 사용자이력ID

  private String scheId; // 스케줄아이디

  private String scheGroup; // 스케줄러 그뤂명

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime startTime; // 시작시간

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime endTime; // 종료시간

  private long execTime; // 실행시간

  private String success; // 성공여부

  private String errorMsg; // 실패사유

}