package com.basic.app.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.basic.app.entity.User;
import com.basic.app.util.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * @파일명 : CustomUserDetails.java
 * @설명 : Spring Security UserDetails 구현 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Getter
@Setter
public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private User user;

  /**
   * @기능 : CustomUserDetails 생성자
   * @param user 사용자 엔티티 객체
   */
  public CustomUserDetails(User user) {
    this.user = user;
  }

  /**
   * @기능 : 사용자의 권한 목록을 반환
   * @return 권한 목록
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();
    collect.add(() -> user.getRole());

    user.getRoleUsers().stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE) && entity.getUseYn().equals("Y"))
        .toList()
        .forEach(roleUser -> {
          collect.add(() -> roleUser.getRole().getRoleCd());
        });

    return collect;
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
