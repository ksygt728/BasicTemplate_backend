/**
 * @파일명   : TimeKeeper.java
 * @설명     : 시간 관련 유틸리티 클래스  
 * @작성자   : 김승연
 * @작성일   : 2025.08.08
 * @변경이력 :
 *   2025.08.08     김승연       최초 생성
 */

package com.basic.app.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@Component
public class TimeKeeper {

  private String splitChar = "~"; // 기간 구분자

  public LocalDateTime convertStringToLocalDateTimeAtStart(String dateTimeStr) {
    try {
      String[] dates = dateTimeStr.split(splitChar);
      if (dates.length == 2) {
        String start = dates[0].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        return startDateTime;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BusinessException(ErrorCode.DATEFORMAT_INVALID);
    }
    return null;
  }

  public LocalDateTime convertStringToLocalDateTimeAtEnd(String dateTimeStr) {
    try {
      String[] dates = dateTimeStr.split(splitChar);
      if (dates.length == 2) {
        String end = dates[1].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        return endDateTime;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BusinessException(ErrorCode.DATEFORMAT_INVALID);
    }
    return null;
  }

  public LocalDateTime convertLongToLocalDateTime(Long timestamp) {
    if (timestamp == null)
      return null;

    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.of("Asia/Seoul"))
        .toLocalDateTime();
  }

}
