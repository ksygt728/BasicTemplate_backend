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

/**
 * @파일명 : WebSvc.java
 * @설명 : 웹서비스 정보 엔티티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
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