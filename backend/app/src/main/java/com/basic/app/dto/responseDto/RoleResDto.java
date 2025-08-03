/**
 * @파일명   : RoleResDto.java
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
public class RoleResDto {

  private String roldCd; // 권한코드

  private String roleName; // 권한명

  private String roleDesc; // 권한설명

  // Role - RoleMenu (1:N)
  // private List<RoleMenuResDto> roleMenus = new ArrayList<RoleMenuResDto>(); // 권한이 가진 메뉴 리스트

  // Role - RoleUser (1:N)
  // private List<RoleUserResDto> roleUsers = new ArrayList<RoleUserResDto>(); // 권한이 가진 사용자 리스트

}