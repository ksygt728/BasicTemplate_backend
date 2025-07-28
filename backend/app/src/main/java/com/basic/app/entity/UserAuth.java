package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity(name = "TB_USER_AUTH") // 사용자 인증 테이블
public class UserAuth extends BaseEntity {

  @Id
  @Column(name = "USER_ID", length = 45)
  private String userId; // 사용자아이디

  @Column(name = "REFRESH_TOKEN", length = 200)
  private String refreshToken; // 리프레시토큰

}