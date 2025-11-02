package com.basic.app.dto.responseDto.specialDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : ComCodeAttributesAndValues.java
 * @설명 : 공통코드 속성 및 값 DTO
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
public class ComCodeAttributesAndValues {
  private String attrCd; // 속성코드
  private String attrNm; // 속성명
  private String dtlNm; // 상세코드명
  private int attrOrderNum; // 속성 정렬순서

}
