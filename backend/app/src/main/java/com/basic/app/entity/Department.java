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
@Entity(name = "TB_DEPARTMENT") // 부서 테이블
public class Department {

  @Id
  @Column(name = "DEPT_CODE", length = 45)
  private String deptCode; // 부서코드

  @Column(name = "DEPT_NM", length = 100, nullable = false)
  private String deptNm; // 부서명

  @Column(name = "UPPER_DEPT_CODE", length = 45, nullable = false)
  private String upperDeptCode; // 상위부서코드

  @Column(name = "DEPT_LV", nullable = false)
  private int deptLv; // 부서레벨

  // Department - Company (N:1) [Onwer]
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COMPANY_CODE", nullable = false)
  private Company companyCode; // 회사코드

  @Column(name = "USE_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

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

  // Department - User (1:N)
  @OneToMany(mappedBy = "deptCode", fetch = FetchType.LAZY)
  private List<User> deptUsers = new ArrayList<User>(); // 부서 사원 리스트

}