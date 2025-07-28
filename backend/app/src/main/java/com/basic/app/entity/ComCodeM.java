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
@Entity(name = "TB_COM_CODE_M") // 공통코드 마스터 테이블
public class ComCodeM extends BaseEntity {
  @Id
  @Column(name = "GRP_CD", length = 45)
  private String grpCd; // 그룹코드

  @Column(name = "GRP_CD_TYPE", length = 45, nullable = false)
  private String grpCdType; // 그룹코드유형

  @Column(name = "GRP_NM", length = 100, nullable = false)
  private String grpNm; // 그룹코드명

  // CodeM - CodeT (1:N)
  @OneToMany(mappedBy = "grpCd", fetch = FetchType.LAZY)
  private List<ComCodeT> comCodeTs = new ArrayList<ComCodeT>(); // 그뤂코드에 포함된 그뤂코드속성 리스트

}