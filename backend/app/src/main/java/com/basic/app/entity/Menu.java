package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : Menu.java
 * @설명 : 메뉴 정보 엔티티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_MENU") // 메뉴 테이블
public class Menu extends BaseEntity {

  @Id
  @Column(name = "MENU_CD", length = 45)
  private String menuCd; // 메뉴코드

  @Column(name = "MENU_NM", length = 100, nullable = false)
  private String menuNm; // 메뉴명

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "UPPER_MENU_CD")
  private Menu upperMenu; // 상위메뉴코드

  @Column(name = "MENU_LV", nullable = false)
  private int menuLv; // 메뉴레벨

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "MENU_URL", length = 200)
  private String menuUrl; // 메뉴 URL

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

  // Menu - 재귀 (1:N)
  @OneToMany(mappedBy = "upperMenu", fetch = FetchType.LAZY)
  private List<Menu> subMenus = new ArrayList<Menu>(); // 메뉴 하위 목록

  // Menu - RoleMenu (1:N)
  @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
  private List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(); // 메뉴가 가진 권한 리스트

  /**
   * @기능 : Entity를 DTO로 변환
   * @param menu 변환할 Menu 엔티티
   * @return 변환된 MenuResDto 객체
   */
  public MenuResDto toDto(Menu menu) {
    return MenuResDto.builder()
        .menuCd(menu.getMenuCd())
        .menuNm(menu.getMenuNm())
        .upperMenu(menu.getUpperMenu() != null ? menu.getUpperMenu().getMenuCd() : null)
        .menuLv(menu.getMenuLv())
        .useYn(menu.getUseYn())
        .menuUrl(menu.getMenuUrl())
        .orderNum(menu.getOrderNum())
        .childMenus(new ArrayList<MenuResDto>())
        .build();
  }

}