package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true) // JwtAuthenticationFilter.java 에서 jackson ObejctMapper null값이 들어가는것을 무시
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_USER") // 사용자 테이블
public class User extends BaseEntity {

  @Id
  @Column(name = "USER_ID", length = 45)
  private String userId; // 사용자아이디

  @Column(name = "PASSWORD", length = 200, nullable = false)
  private String password; // 비밀번호

  @Column(name = "NAME", length = 100, nullable = false)
  private String name; // 이름

  @Column(name = "PHONE_NUM", length = 45, nullable = false)
  private String phoneNum; // 전화번호

  @Column(name = "EMAIL", length = 45, nullable = false)
  private String email; // 이메일

  @Column(name = "ROLE", length = 45, nullable = false)
  private String role; // 역할

  @Column(name = "USER_TYPE", length = 45, nullable = false)
  private String userType; // 사용자타입

  @Column(name = "GENDER", length = 1, nullable = false)
  private String gender; // 성별 (M, F)

  // User - Department (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEPT_CODE")
  private Department department; // 부서코드

  // User - Bbs (1:N)
  @OneToMany(mappedBy = "writor", fetch = FetchType.LAZY)
  private List<Bbs> bbsWritors = new ArrayList<Bbs>(); // 작성한 게시글 리스트

  // User - BbsComment (1:N)
  @OneToMany(mappedBy = "writor", fetch = FetchType.LAZY)
  private List<BbsComment> bbsCommentWritors = new ArrayList<BbsComment>(); // 작성한 댓글 리스트

  // User - Notice (1:N)
  @OneToMany(mappedBy = "writor", fetch = FetchType.LAZY)
  private List<Notice> noticeWritors = new ArrayList<Notice>(); // 작성한 공지 리스트

  // User - RoleUser (1:N)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<RoleUser> roleUsers = new ArrayList<RoleUser>(); // 사용자가 가진 권한 리스트

}
