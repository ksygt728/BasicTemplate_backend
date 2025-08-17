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
@Table(name = "TB_BBS_COMMENT") // 게시판 댓글 테이블
public class BbsComment extends BaseEntity {

  @Id
  @Column(name = "COMMENT_ID", length = 45)
  private String commentId; // 댓글아이디

  // BbsComemnt - Bbs (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BBS_ID", nullable = false)
  private Bbs bbsId; // 게시판아이디

  @Column(name = "CONTENT", length = 2048, nullable = false)
  private String content; // 내용

  // BbsComment - User (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "WRITOR", nullable = false)
  private User writor; // 작성자

  @Column(name = "WRITE_DATE", columnDefinition = "TIMESTAMP(3)", nullable = false)
  private LocalDateTime writeDate; // 작성일

}