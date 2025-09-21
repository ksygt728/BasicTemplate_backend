package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : MenuResDto.java
 * @설명 : 메뉴 정보 응답 데이터 전송 객체
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuResDto {

  private String menuCd; // 메뉴코드

  private String menuNm; // 메뉴명

  private String upperMenu; // 상위메뉴코드

  private int menuLv; // 메뉴레벨

  private String useYn; // 사용여부 (Y,N)

  private String menuUrl; // 메뉴 URL

  private int orderNum; // 정렬순서

  private List<MenuResDto> childMenus = new ArrayList<MenuResDto>(); // 메뉴 하위 목록

  // Menu - RoleMenu (1:N)
  // private List<RoleMenuResDto> roleMenus = new ArrayList<RoleMenuResDto>(); //
  // 메뉴가 가진 권한 리스트

}