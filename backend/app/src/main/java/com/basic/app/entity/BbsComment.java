package com.basic.app.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "TB_BBS_COMMENT") // 게시판 댓글 테이블
public class BbsComment extends BaseEntity {

  @Id
  @Column(name = "COMMENT_ID", length = 36)
  private String commentId; // 댓글아이디

  @PrePersist
  public void prePersist() {
    if (commentId == null) {
      commentId = UUID.randomUUID().toString(); // "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
    }
  }

  // BbsComemnt - Bbs (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BBS_ID", nullable = false)
  private Bbs bbs; // 게시판아이디

  @Column(name = "CONTENT", length = 2048, nullable = false)
  private String commentContent; // 내용

  // BbsComment - User (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "WRITOR", nullable = false, updatable = false)
  private User writor; // 작성자

  @CreatedDate
  @Column(name = "WRITE_DATE", updatable = false, columnDefinition = "TIMESTAMP(3)", nullable = false)
  private LocalDateTime writeDate; // 작성일

}