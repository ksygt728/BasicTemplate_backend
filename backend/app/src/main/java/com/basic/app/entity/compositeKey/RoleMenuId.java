package com.basic.app.entity.compositeKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : RoleMenuId.java
 * @설명 : 역할-메뉴 관계 복합키 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode // 복합키는 equals, hashcode 필수 생성
public class RoleMenuId implements Serializable {

  @Column(name = "ROLE_CD", length = 45)
  private String roleCd;

  @Column(name = "MENU_CD", length = 45)
  private String menuCd;

}