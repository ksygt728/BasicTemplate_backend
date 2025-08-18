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
@Entity
@Table(name = "TB_ROLE") // 권한 테이블
public class Role extends BaseEntity {

  @Id
  @Column(name = "ROLD_CD", length = 45)
  private String roldCd; // 권한코드

  @Column(name = "ROLE_NAME", length = 100, nullable = false)
  private String roleName; // 권한명

  @Column(name = "ROLE_DESC", length = 1024)
  private String roleDesc; // 권한설명

  // Role - RoleMenu (1:N)
  @OneToMany(mappedBy = "roleCd", fetch = FetchType.LAZY)
  private List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(); // 권한이 가진 메뉴 리스트

  // Role - RoleUser (1:N)
  @OneToMany(mappedBy = "roleCd", fetch = FetchType.LAZY)
  private List<RoleUser> roleUsers = new ArrayList<RoleUser>(); // 권한이 가진 사용자 리스트

}