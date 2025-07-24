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
public class DepartmentReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서코드는 필수입니다.")
  private String deptCode; // 부서코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서코드명은 필수입니다.")
  private String deptNm; // 부서명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상위부서코드는 필수입니다.")
  private String upperDeptCode; // 상위부서코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서레벨은 필수입니다.")
  private int deptLv; // 부서레벨

  // Department - Company (N:1) [Onwer]
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "회사코드은 필수입니다.")
  private String companyCode; // 회사코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자
}