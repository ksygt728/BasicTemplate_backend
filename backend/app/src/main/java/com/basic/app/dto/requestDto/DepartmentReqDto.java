/**
 * @파일명   : DepartmentReqDto.java
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

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentReqDto extends BaseReqDto {

  @Schema(description = "부서코드", example = "DEPT001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서코드는 필수입니다.")
  private String deptCode; // 부서코드

  @Schema(description = "부서명", example = "개발팀")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서코드명은 필수입니다.")
  private String deptNm; // 부서명

  @Schema(description = "상위부서코드", example = "DEPT000")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상위부서코드는 필수입니다.")
  private String upperDeptCode; // 상위부서코드

  @Schema(description = "부서레벨", example = "1")
  @Min(value = 0, groups = { CreateGroup.class, UpdateGroup.class }, message = "부서레벨은 0 이상이어야 합니다.")
  @NotNull(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서레벨은 필수입니다.")
  private int deptLv; // 부서레벨

  // Department - Company (N:1) [Onwer]
  @Schema(description = "회사코드", example = "COMP001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "회사코드은 필수입니다.")
  private String companyCode; // 회사코드

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

}