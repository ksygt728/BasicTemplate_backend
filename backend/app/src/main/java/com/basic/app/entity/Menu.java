package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

  @Column(name = "UPPER_MENU_CD", length = 45, nullable = false)
  private String upperMenuCd; // 상위메뉴코드

  @Column(name = "MENU_LV", nullable = false)
  private int menuLv; // 메뉴레벨

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "MENU_URL", length = 200)
  private String menuUrl; // 메뉴 URL

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

  // Menu - RoleMenu (1:N)
  @OneToMany(mappedBy = "menuCd", fetch = FetchType.LAZY)
  private List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(); // 메뉴가 가진 권한 리스트

}