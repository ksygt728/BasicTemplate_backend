
package com.basic.app.entity.compositeKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : ComCodeDId.java
 * @설명 : 공통코드 상세 복합키 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
@EqualsAndHashCode // 복합키는 equals, hashcode 필수 생성
public class ComCodeDId implements Serializable {

  @Embedded
  private ComCodeTId comCodeTId; // 부모 키 포함(그뤂코드, 속성코드)

  @Column(name = "DTL_CD", length = 45)
  private String dtlCd; // 상세코드

}