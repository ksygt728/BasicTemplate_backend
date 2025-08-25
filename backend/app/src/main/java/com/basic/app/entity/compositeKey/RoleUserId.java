package com.basic.app.entity.compositeKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode // 복합키는 equals, hashcode 필수 생성
public class RoleUserId implements Serializable {

  @Column(name = "ROLE_CD", length = 45)
  private String roleCd;

  @Column(name = "USER_ID", length = 45)
  private String userId;

}