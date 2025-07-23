package com.basic.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity(name = "TB_BBS") // 게시판 테이블
public class Bbs {

  @Id
  @Column(name = "BBS_ID", length = 45)
  private String bbsId; // 게시판아이디

  @Column(name = "BBS_TYPE", length = 45, nullable = false)
  private String bbsType; // 게시판타입

  @Column(name = "TITLE", length = 100, nullable = false)
  private String title; // 제목

  @Column(name = "CONTENT", length = 2048, nullable = false)
  private String content; // 내용

  // Bbs - User (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "WRITOR", nullable = false)
  private User writor; // 작성자

  @Column(name = "WRITE_DATE", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime writeDate; // 작성일

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

  // Bbs - BbsComment (1:N)
  @OneToMany(mappedBy = "bbsId", fetch = FetchType.LAZY)
  private List<BbsComment> bbsComments = new ArrayList<BbsComment>(); // 게시글 댓글 리스트

}