package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.RoleMenuId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "TB_ROLE_MENU") // 권한-메뉴 매핑 테이블
public class RoleMenu extends BaseEntity {

  @EmbeddedId
  private RoleMenuId roleMenuId;

  // RoleMenu - Role (N:1) [Onwer]
  @MapsId("roleCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_CD")
  private Role role;

  // RoleMenu - Menu (N:1) [Onwer]
  @MapsId("menuCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MENU_CD")
  private Menu menu;

  @Column(name = "MENU_RW", length = 45, nullable = false)
  private String menuRw; // 메뉴 접근 수준 (R,W)

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

}