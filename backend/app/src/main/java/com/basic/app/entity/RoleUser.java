package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.compositeKey.RoleMenuId;
import com.basic.app.entity.compositeKey.RoleUserId;

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
@Entity(name = "TB_ROLE_USER") // 권한-사용자 매핑 테이블
public class RoleUser {

  @EmbeddedId
  private RoleUserId roleUserId;

  // RoleUser - Role (N:1) [Onwer]
  @MapsId("roldCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLD_CD")
  private Role roleCd;

  // RoleUser - User (N:1) [Onwer]
  @MapsId("userId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User userId;

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