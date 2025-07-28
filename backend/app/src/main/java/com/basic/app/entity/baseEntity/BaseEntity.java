package com.basic.app.entity.baseEntity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Column(name = "STS", nullable = false, insertable = false, columnDefinition = "CHAR(1) DEFAULT 'C'")
  private String sts = "C"; // 시스템 상태 (C, D)

  @CreatedBy
  @Column(name = "CREATE_USER", length = 45, nullable = false, updatable = false) // update할때 들어가면 안됨
  private String createUser; // 생성자

  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "CREATE_DATE", nullable = false, updatable = false) // update할때 들어가면 안됨
  private LocalDateTime createDate; // 생성일시

  @LastModifiedBy
  @Column(name = "UPDATE_USER", length = 45, nullable = false)
  private String updateUser; // 수정자

  @LastModifiedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "TIMESTAMP", nullable = false)
  private LocalDateTime timestamp; // 수정일시
}