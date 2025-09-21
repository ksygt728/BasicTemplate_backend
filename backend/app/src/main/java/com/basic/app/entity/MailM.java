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
 * @파일명 : MailM.java
 * @설명 : 메일 템플릿 엔티티 클래스
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
@Table(name = "TB_MAIL_M") // 메일 템플릿 테이블
public class MailM extends BaseEntity {
  @Id
  @Column(name = "MAIL_ID", length = 45)
  private String mailId; // 메일아이디

  @Column(name = "LANG_TYPE", length = 45, nullable = false)
  private String langType; // 언어타입

  @Column(name = "MAIL_NAME", length = 100, nullable = false)
  private String mailName; // 메일명

  @Column(name = "TITLE", length = 100, nullable = false)
  private String title; // 제목

  @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
  private String content; // 내용

  @Column(name = "DESCRIPTION", length = 2048)
  private String description; // 설명

}