package com.basic.app.dto.responseDto.specialDto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : ComCodeInfo.java
 * @설명 : 공통코드 정보 DTO
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
public class ComCodeInfo {

  private String dtlCd; // 상세코드

  @Builder.Default
  private List<ComCodeAttributesAndValues> codeAttributes = new ArrayList<>(); // 속성 정보

  private String useYn; // 사용여부 (Y,N)

  private int dtlOrderNum; // 정렬순서
}
