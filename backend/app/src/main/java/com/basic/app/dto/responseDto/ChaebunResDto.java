/**
 * @파일명   : ChaebunResDto.java
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
public class ChaebunResDto {

  private String seqId; // 채번아이디

  private String seqName; // 채번명

  private String pattern; // 채번패턴

  private String prefix; // 채번고유번호

  private int currentValue; // 현재 채번값

  private int step; // 증가량

  private int length; // 채번길이

  private String dateformat; // 데이터포맷

}