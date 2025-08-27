package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.MulLangId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_MUL_LANG") // 다국어 테이블
public class MulLang extends BaseEntity {

  @EmbeddedId
  private MulLangId mulLang; // 복합키: 언어코드, 언어유형

  @Column(name = "LANG_NM", length = 2048, nullable = false)
  private String langNm; // 언어명

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

}