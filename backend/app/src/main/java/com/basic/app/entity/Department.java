package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.baseEntity.BaseEntity;

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
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TB_DEPARTMENT") // 부서 테이블
public class Department extends BaseEntity {

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
  private Company company; // 회사코드

  @Column(name = "USE_YN", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  // Department - User (1:N)
  @OneToMany(mappedBy = "deptCode", fetch = FetchType.LAZY)
  private List<User> deptUsers = new ArrayList<User>(); // 부서 사원 리스트

}