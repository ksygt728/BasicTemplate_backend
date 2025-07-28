/**
 * @파일명   : ComCodeTResDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class ComCodeTResDto {

  private String grpCd; // 그룹코드

  private String attrCd; // 속성코드

  private String attrNm; // 속성명

  private int orderNum; // 정렬순서

  // CodeT - CodeD (1:N)
  private List<ComCodeDResDto> comCodeDs = new ArrayList<ComCodeDResDto>(); // 속성코드에 포함된 상세코드 리스트

}