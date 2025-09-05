
package com.basic.app.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : TimeKeeper.java
 * @설명 : 시간 관련 유틸리티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.08.08
 * @변경이력 :
 *       2025.08.08 김승연 최초 생성
 */

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@Component
public class TimeKeeper {

  private String splitChar = "~"; // 기간 구분자

  /**
   * @기능 : 날짜 문자열에서 시작 시간을 LocalDateTime으로 변환
   * @param dateTimeStr 날짜 문자열 (시작~끝 형식)
   * @return 시작 시간 LocalDateTime
   */
  public LocalDateTime convertStringToLocalDateTimeAtStart(String dateTimeStr) {
    try {
      String[] dates = dateTimeStr.split(splitChar);
      if (dates.length == 2) {
        String start = dates[0].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        return startDateTime;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BusinessException(ErrorCode.DATEFORMAT_INVALID);
    }
    return null;
  }

  /**
   * @기능 : 날짜 문자열에서 종료 시간을 LocalDateTime으로 변환
   * @param dateTimeStr 날짜 문자열 (시작~끝 형식)
   * @return 종료 시간 LocalDateTime
   */
  public LocalDateTime convertStringToLocalDateTimeAtEnd(String dateTimeStr) {
    try {
      String[] dates = dateTimeStr.split(splitChar);
      if (dates.length == 2) {
        String end = dates[1].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        return endDateTime;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BusinessException(ErrorCode.DATEFORMAT_INVALID);
    }
    return null;
  }

  /**
   * @기능 : Long 타임스탬프를 LocalDateTime으로 변환
   * @param timestamp 타임스탬프
   * @return LocalDateTime 객체
   */
  public LocalDateTime convertLongToLocalDateTime(Long timestamp) {
    if (timestamp == null)
      return null;

    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.of("Asia/Seoul"))
        .toLocalDateTime();
  }

  /**
   * @기능 : Date를 LocalDateTime으로 변환
   * @param date Date 객체
   * @return LocalDateTime 객체
   */
  public LocalDateTime convertDateToLocalDateTime(Date date) {
    if (date == null)
      return null;

    LocalDateTime localDateTime = date.toInstant()
        .atZone(ZoneId.of("Asia/Seoul"))
        .toLocalDateTime();

    // 밀리초까지 포맷해서 출력
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    String formattedDateTime = localDateTime.format(formatter);
    return LocalDateTime.parse(formattedDateTime, formatter);
  }

  /**
   * @기능 : 문자열을 LocalDateTime으로 변환
   * @param date 날짜 문자열
   * @return LocalDateTime 객체
   */
  public LocalDateTime convertStringToLocalDateTime(String date) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
    return localDateTime;
  }

}
