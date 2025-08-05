/**
 * @파일명   : DepartmentResDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.Company;
import com.fasterxml.jackson.annotation.JsonFormat;

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
public class DepartmentResDto {

  private String deptCode; // 부서코드

  private String deptNm; // 부서명

  private String upperDeptCode; // 상위부서코드

  private int deptLv; // 부서레벨

  // Department - Company (N:1) [Onwer]
  private CompanyResDto company; // 회사코드

  private String useYn; // 사용여부 (Y,N)

  // Department - User (1:N)
  // private List<UserResDto> deptUsers = new ArrayList<UserResDto>(); // 부서 사원
  // 리스트

}