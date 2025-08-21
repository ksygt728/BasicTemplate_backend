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

import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(description = "그룹코드유형", example = "TYPE01")
  private String grpCdType; // 그룹코드유형

  @Schema(description = "그룹코드", example = "GRP001")
  private String grpCd; // 그룹코드

  @Schema(description = "그룹코드명", example = "공통코드그룹")
  private String grpNm; // 그룹코드명

  @Schema(description = "속성코드", example = "ATTR001")
  private String attrCd; // 속성코드

  @Schema(description = "속성명", example = "공통속성")
  private String attrNm; // 속성명

  @Schema(description = "상세코드", example = "DTL001")
  private String dtlCd; // 상세코드

  @Schema(description = "상세코드명", example = "상세코드이름")
  private String dtlNm; // 상세코드명

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  private String useYn; // 상세코드명

}