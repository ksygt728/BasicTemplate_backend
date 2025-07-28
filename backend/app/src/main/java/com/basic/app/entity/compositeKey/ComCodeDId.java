
package com.basic.app.entity.compositeKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode // 복합키는 equals, hashcode 필수 생성
public class ComCodeDId implements Serializable {

  @Embedded
  private ComCodeTId comCodeTId; // 부모 키 포함(그뤂코드, 속성코드)

  @Column(name = "DTL_CD", length = 45)
  private String dtlCd; // 상세코드

}