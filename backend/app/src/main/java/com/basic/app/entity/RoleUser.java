package com.basic.app.entity;

import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.responseDto.ComCodeMResDto;
import com.basic.app.dto.responseDto.ComCodeTResDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.ComCodeDId;
import com.basic.app.entity.compositeKey.ComCodeTId;
import com.basic.app.entity.compositeKey.RoleUserId;
import com.basic.app.util.Status;

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

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_ROLE_USER") // 권한-사용자 매핑 테이블
public class RoleUser extends BaseEntity {

  @EmbeddedId
  private RoleUserId roleUserId;

  // RoleUser - Role (N:1) [Onwer]
  @MapsId("roleCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_CD")
  private Role role;

  // RoleUser - User (N:1) [Onwer]
  @MapsId("userId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  public RoleUserResDto toDto(RoleUser entity) {
    return RoleUserResDto.builder()
        .userId(entity.getRoleUserId().getUserId())
        .role(RoleResDto.builder()
            .roleCd(entity.getRole().getRoleCd())
            .roleName(entity.getRole().getRoleName())
            .roleDesc(entity.getRole().getRoleDesc())
            .build())
        .useYn(entity.getUseYn())
        .build();
  }

}