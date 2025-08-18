package com.basic.app.dto.responseDto.specialDto;

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
public class ComCodeAttributesAndValues {
  private String attrCd; // 속성코드
  private String attrNm; // 속성명
  private String dtlNm; // 상세코드명

}
