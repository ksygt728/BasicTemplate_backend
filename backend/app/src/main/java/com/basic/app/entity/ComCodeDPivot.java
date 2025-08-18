package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.ComCodeDPivotId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "TB_COM_CODE_D_PIVOT") // 공통코드 상세 테이블
public class ComCodeDPivot extends BaseEntity {

  @EmbeddedId
  private ComCodeDPivotId comCodeDPivotId;

  // CodeD - CodeM (N:1) [Onwer]
  @MapsId("comCodeDPivotId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GRP_CD", referencedColumnName = "GRP_CD")
  private ComCodeM grpCd;

  @Column(name = "ATTR_01", length = 200)
  private String attr01; // 상세코드값

  @Column(name = "ATTR_02", length = 200)
  private String attr02; // 상세코드값

  @Column(name = "ATTR_03", length = 200)
  private String attr03; // 상세코드값

  @Column(name = "ATTR_04", length = 200)
  private String attr04; // 상세코드값

  @Column(name = "ATTR_05", length = 200)
  private String attr05; // 상세코드값

  @Column(name = "ATTR_06", length = 200)
  private String attr06; // 상세코드값

  @Column(name = "ATTR_07", length = 200)
  private String attr07; // 상세코드값

  @Column(name = "ATTR_08", length = 200)
  private String attr08; // 상세코드값

  @Column(name = "ATTR_09", length = 200)
  private String attr09; // 상세코드값

  @Column(name = "ATTR_10", length = 200)
  private String attr10; // 상세코드값

  @Column(name = "USE_YN", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

}