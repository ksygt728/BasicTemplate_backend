package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "TB_WEB_SVC") // 웹서비스 테이블
public class WebSvc extends BaseEntity {
  @Id
  @Column(name = "SVC_ID", length = 45)
  private String svcId; // 웹서비스 아이디

  @Column(name = "SVC_NAME", length = 100, nullable = false)
  private String svcName; // 웹서비스명

}