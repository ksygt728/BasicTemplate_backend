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

  private String prefix; // 채번고유번호

  private int currentValue; // 현재 채번값

  private int step; // 증가량

  private int length; // 채번길이

  private String dateformat; // 데이터포맷

  private String sts; // 시스템 상태 (C, D)

  private String createUser; // 생성자

  private LocalDateTime createDate; // 생성일

  private String updateUser; // 수정자

  private LocalDateTime timestamp; // 수정일
}