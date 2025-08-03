/**
 * @파일명   : MenuResDto.java
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
public class MenuResDto {

  private String menuCd; // 메뉴코드

  private String menuNm; // 메뉴명

  private String upperMenuCd; // 상위메뉴코드

  private int menuLv; // 메뉴레벨

  private String useYn; // 사용여부 (Y,N)

  private String menuUrl; // 메뉴 URL

  private int orderNum; // 정렬순서

  // Menu - RoleMenu (1:N)
  // private List<RoleMenuResDto> roleMenus = new ArrayList<RoleMenuResDto>(); //
  // 메뉴가 가진 권한 리스트

}