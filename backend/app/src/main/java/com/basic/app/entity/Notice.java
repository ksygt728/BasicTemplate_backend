package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "TB_NOTICE") // 공지 테이블
public class Notice extends BaseEntity {

  @Id
  @Column(name = "NOT_ID", length = 45)
  private String notId; // 공지아이디

  @Column(name = "NOT_TYPE", length = 45, nullable = false)
  private String notType; // 공지타입 (공지, 매뉴얼)

  @Column(name = "TITLE", length = 100, nullable = false)
  private String title; // 제목

  @Column(name = "CONTENT", length = 2048, nullable = false)
  private String content; // 내용

  // Notice - User (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "WRITOR", nullable = false)
  private User writor; // 작성자

  @Column(name = "WRITE_DATE", columnDefinition = "TIMESTAMP(3)", nullable = false)
  private LocalDateTime writeDate; // 작성일

}