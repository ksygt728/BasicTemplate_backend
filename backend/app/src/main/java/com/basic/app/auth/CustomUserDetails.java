package com.basic.app.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.basic.app.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @파일명 : CustomUserDetails.java
 * @설명 : Spring Security UserDetails 구현 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 *       2025.12.14 김승연 RBAC방식의 권한 체크로 인한 권한 리스트 추가 및 세부 로직 변경
 */
@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private User user;

  /** ROLE_ADMIN, ROLE_USER ... */
  private List<String> roles;

  /** MENU001_READ, MENU001_WRITE ... */
  private List<String> permissions;

  public CustomUserDetails(User user) {
    this.user = user;
    this.roles = new ArrayList<>();
    this.permissions = new ArrayList<>();
  }

  /**
   * @기능 : 사용자의 역할 목록을 반환
   * @return 역할(Role) 목록
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();

    if (roles != null && !roles.isEmpty()) {
      collect.addAll(roles.stream()
          .map(role -> (GrantedAuthority) () -> role)
          .toList());
    }
    return collect;
  }

  /**
   * @기능 : 사용자의 권한 목록을 반환
   * @return 권한 목록
   */
  public List<String> getPermissions() {

    return permissions;
  }

  /**
   * @기능 : 사용자의 비밀번호를 반환
   * @return 사용자 비밀번호
   */
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  /**
   * @기능 : 사용자의 사용자명(ID)을 반환
   * @return 사용자 ID
   */
  @Override
  public String getUsername() {
    return user.getUserId();
  }

  /**
   * @기능 : 계정 만료 여부 확인
   * @return 계정 만료되지 않음 (항상 true)
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * @기능 : 계정 잠금 여부 확인
   * @return 계정 잠기지 않음 (항상 true)
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * @기능 : 비밀번호 만료 여부 확인
   * @return 비밀번호 만료되지 않음 (항상 true)
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * @기능 : 계정 활성화 여부 확인
   * @return 계정 활성화됨 (항상 true)
   */
  @Override
  public boolean isEnabled() {
    return true;
  }

}
