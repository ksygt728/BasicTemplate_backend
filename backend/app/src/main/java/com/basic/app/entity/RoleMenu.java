package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.compositeKey.RoleMenuId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Entity(name = "TB_ROLE_MENU") // 권한-메뉴 매핑 테이블
public class RoleMenu {

  @EmbeddedId
  private RoleMenuId roleMenuId;

  // RoleMenu - Role (N:1) [Onwer]
  @MapsId("roldCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLD_CD")
  private Role roleCd;

  // RoleMenu - Menu (N:1) [Onwer]
  @MapsId("menuCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MENU_CD")
  private Menu menuCd;

  @Column(name = "MENU_RW", length = 45, nullable = false)
  private String menuRw; // 메뉴 접근 수준 (R,W)

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

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
}