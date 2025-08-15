/**
 * @파일명   : CodeSearchFormReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.08.15
 * @변경이력 :
 *   2025.08.15     김승연       최초 생성
 */
package com.basic.app.dto.requestDto.specialDto;

import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

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
public class CodeSearchFormReqDto extends BaseReqDto {

  private String grpCdType; // 그룹코드유형

  private String grpCd; // 그룹코드

  private String grpNm; // 그룹코드명

  private String attrCd; // 속성코드

  private String attrNm; // 속성명

  private String dtlCd; // 상세코드

  private String dtlNm; // 상세코드명

  private String useYn; // 상세코드명

}