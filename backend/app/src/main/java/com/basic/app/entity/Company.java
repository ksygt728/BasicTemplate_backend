package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Entity(name = "TB_COMPANY") // 회사 테이블
public class Company extends BaseEntity {

  @Id
  @Column(name = "COMPANY_CODE", length = 45)
  private String companyCode; // 회사코드

  @Column(name = "COMPANY_NAME", length = 100, nullable = false)
  private String companyName; // 회사명

  // Company - Department (1:N)
  @OneToMany(mappedBy = "companyCode", fetch = FetchType.LAZY)
  private List<Department> dpets = new ArrayList<Department>(); // 부서 리스트
}