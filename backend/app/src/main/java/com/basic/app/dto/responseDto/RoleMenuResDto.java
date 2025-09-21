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

/**
 * @파일명 : RoleMenuResDto.java
 * @설명 : 역할-메뉴 관계 정보 응답 데이터 전송 객체
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
public class RoleMenuResDto {

  private String menuCd; // 메뉴코드

  private String menuNm; // 메뉴명

  private String upperMenu; // 상위메뉴코드

  private int menuLv; // 메뉴레벨

  private String useYn; // 사용여부 (Y,N)

  private String menuUrl; // 메뉴 URL

  private int orderNum; // 정렬순서

  private String menuRw; // 메뉴 접근 수준 (R,W)

  private List<RoleMenuResDto> childMenus = new ArrayList<RoleMenuResDto>(); // 메뉴 하위 목록

}