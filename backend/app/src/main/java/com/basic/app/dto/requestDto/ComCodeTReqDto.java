/**
 * @파일명   : ComCodeTReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

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
public class ComCodeTReqDto extends BaseReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "그뤂코드는 필수입니다.")
  private String grpCd; // 그룹코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "속성코드는 필수입니다.")
  private String attrCd; // 속성코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "속성명은 필수입니다.")
  private String attrNm; // 속성명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "정렬순서는 필수입니다.")
  private int orderNum; // 정렬순서

}