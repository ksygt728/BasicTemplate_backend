package com.basic.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity(name = "TB_MENU") // 메뉴 테이블
public class Menu {

  @Id
  @Column(name = "MENU_CD", length = 45)
  private String menuCd; // 메뉴코드

  @Column(name = "MENU_NM", length = 100, nullable = false)
  private String menuNm; // 메뉴명

  @Column(name = "UPPER_MENU_CD", length = 45, nullable = false)
  private String upperMenuCd; // 상위메뉴코드

  @Column(name = "MENU_LV", nullable = false)
  private int menuLv; // 메뉴레벨

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "MENU_URL", length = 200)
  private String menuUrl; // 메뉴 URL

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

  @Column(name = "STS", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'C'")
  private String sts; // 시스템 상태 (C, D)

  @Column(name = "CREATE_USER", length = 45, nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'SYSTEM'")
  private String createUser; // 생성자

  @Column(name = "CREATE_DATE", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createDate; // 생성일

  @Column(name = "UPDATE_USER", length = 45, nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'SYSTEM'")
  private String updateUser; // 수정자

  @Column(name = "TIMESTAMP", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime timestamp; // 수정일

  // Menu - RoleMenu (1:N)
  @OneToMany(mappedBy = "menuCd", fetch = FetchType.LAZY)
  private List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(); // 메뉴가 가진 권한 리스트

}