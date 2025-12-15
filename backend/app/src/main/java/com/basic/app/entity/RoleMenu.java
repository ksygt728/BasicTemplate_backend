package com.basic.app.entity;

import java.util.ArrayList;

import com.basic.app.dto.responseDto.RoleMenuResDto;
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

/**
 * @파일명 : RoleMenu.java
 * @설명 : 역할-메뉴 매핑 엔티티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 *       2025.12.14 김승연 menuRw 필드 RW에서 CRUD로 변경(주석만 변경)
 */
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
  private String menuRw; // 메뉴 접근 수준 (C,R,U,D)

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  /**
   * @기능 : 엔티티를 DTO로 변환
   * @param entity RoleMenu 엔티티
   * @return RoleMenuResDto 응답 DTO
   */
  public RoleMenuResDto toDto(RoleMenu entity) {

    return RoleMenuResDto.builder()
        .menuCd(entity.getRoleMenuId().getMenuCd())
        .menuNm(entity.getMenu().getMenuNm())
        .upperMenu(entity.getMenu().getUpperMenu() != null ? entity.getMenu().getUpperMenu().getMenuCd() : null)
        .menuLv(entity.getMenu().getMenuLv())
        .useYn(entity.getUseYn())
        .menuUrl(entity.getMenu().getMenuUrl())
        .orderNum(entity.getMenu().getOrderNum())
        .menuRw(entity.getMenuRw())
        .childMenus(new ArrayList<RoleMenuResDto>())
        .build();
  }

}