package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "TB_MUL_LANG") // 다국어 테이블
public class MulLang extends BaseEntity {
  @Id
  @Column(name = "LANG_CD", length = 45)
  private String langCd; // 언어코드

  @Column(name = "LANG_TYPE", length = 45, nullable = false)
  private String langType; // 언어유형

  @Column(name = "LANG_NM", length = 2048, nullable = false)
  private String langNm; // 언어명

  @Column(name = "LANG_GUBUN", length = 45, nullable = false)
  private String langGubun; // 언어구분

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

}