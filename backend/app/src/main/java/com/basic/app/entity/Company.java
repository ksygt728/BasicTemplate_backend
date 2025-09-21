package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : Company.java
 * @설명 : 회사 정보 엔티티 클래스
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
@Table(name = "TB_COMPANY") // 회사 테이블
public class Company extends BaseEntity {

  @Id
  @Column(name = "COMPANY_CODE", length = 45)
  private String companyCode; // 회사코드

  @Column(name = "COMPANY_NAME", length = 100, nullable = false)
  private String companyName; // 회사명

  // Company - Department (1:N)
  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  private List<Department> dpets = new ArrayList<Department>(); // 부서 리스트
}