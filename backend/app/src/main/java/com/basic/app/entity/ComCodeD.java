package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.ComCodeDId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Entity(name = "TB_COM_CODE_D") // 공통코드 상세 테이블
public class ComCodeD extends BaseEntity {

  @EmbeddedId
  private ComCodeDId comCodeDId;

  // CodeD - CodeT (N:1) [Onwer]
  @MapsId("comCodeTId") // ComCodeDId 클래스 안에 정의된 ComCodeT의 복합키 필드명
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
      @JoinColumn(name = "GRP_CD", referencedColumnName = "GRP_CD"),
      @JoinColumn(name = "ATTR_CD", referencedColumnName = "ATTR_CD")
  })
  private ComCodeT comCodeT;

  @Column(name = "DTL_NM", length = 100, nullable = false)
  private String dtlNm; // 상세코드명

  @Column(name = "USE_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

}