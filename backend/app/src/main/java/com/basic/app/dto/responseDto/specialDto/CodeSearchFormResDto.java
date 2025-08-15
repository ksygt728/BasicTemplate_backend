/**
 * @파일명   : ComCodeDResDto.java
 * @설명     : 공통 코드 상세 정보 응답 데이터 전송 객체
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
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
public class CodeSearchFormResDto {

  private String grpCdType; // 그룹코드유형

  private String grpCd; // 그룹코드

  private String grpNm; // 그룹코드명

  private String dtlCd; // 상세코드

  private String attrCd; // 속성코드

  private String dtlNm; // 상세코드명

  private String attrNm; // 속성명

  private String useYn; // 사용여부 (Y,N)

  private int codeTOrderNum; // 정렬순서

  private int codeDOrderNum; // 정렬순서

}