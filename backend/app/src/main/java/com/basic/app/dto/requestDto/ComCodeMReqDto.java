/**
 * @파일명   : ComCodeMReqDto.java
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
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComCodeMReqDto extends BaseReqDto {

  @Schema(description = "그룹코드", example = "GRP001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "그뤂코드는 필수입니다.")
  private String grpCd; // 그룹코드

  @Schema(description = "그룹코드유형", example = "TYPE01")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "그뤂코드유형은 필수입니다.")
  private String grpCdType; // 그룹코드유형

  @Schema(description = "그룹코드명", example = "공통코드그룹")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "그뤂코드명은 필수입니다.")
  private String grpNm; // 그룹코드명

}