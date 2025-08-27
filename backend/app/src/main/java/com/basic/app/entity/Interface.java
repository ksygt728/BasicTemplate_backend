package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "TB_IF") // 인터페이스 테이블
public class Interface extends BaseEntity {
  @Id
  @Column(name = "IF_ID", length = 45)
  private String ifId; // 인터페이스 아이디

  @Column(name = "IF_NAME", length = 100, nullable = false)
  private String ifName; // 인터페이스명

  @Column(name = "TEXT", length = 100, nullable = false)
  private String text; // WSDL

}