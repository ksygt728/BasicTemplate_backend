package com.basic.app.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.basic.app.entity.User;
import com.basic.app.util.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }

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

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserId();
  }

  // 계정 만료
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // 계정 잠겼는지
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  // 비밀번호 기간 지났는지
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // 계정 활성화 여부
  @Override
  public boolean isEnabled() {
    return true;
  }

}
