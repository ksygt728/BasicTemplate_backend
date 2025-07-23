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
@Entity(name = "TB_ROLE") // 권한 테이블
public class Role {

  @Id
  @Column(name = "ROLD_CD", length = 45)
  private String roldCd; // 권한코드

  @Column(name = "ROLE_NAME", length = 100, nullable = false)
  private String roleName; // 권한명

  @Column(name = "ROLE_DESC", length = 1024)
  private String roleDesc; // 권한설명

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

  // Role - RoleMenu (1:N)
  @OneToMany(mappedBy = "roleCd", fetch = FetchType.LAZY)
  private List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(); // 권한이 가진 메뉴 리스트

  // Role - RoleUser (1:N)
  @OneToMany(mappedBy = "roleCd", fetch = FetchType.LAZY)
  private List<RoleUser> roleUsers = new ArrayList<RoleUser>(); // 권한이 가진 사용자 리스트

}