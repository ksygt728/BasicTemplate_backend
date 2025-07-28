/**
 * @파일명   : ComCodeDReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;

import jakarta.validation.constraints.NotBlank;
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
public class ComCodeDReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "그뤂코드는 필수입니다.")
  private String grpCd; // 그룹코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "속성코드는 필수입니다.")
  private String attrCd; // 속성코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상세코드는 필수입니다.")
  private String dtlCd; // 상세코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상세코드명 필수입니다.")
  private String dtlNm; // 상세코드명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "정렬순서는 필수입니다.")
  private int orderNum; // 정렬순서

}