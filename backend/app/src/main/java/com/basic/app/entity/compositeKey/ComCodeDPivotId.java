package com.basic.app.entity.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : ComCodeDPivotId.java
 * @설명 : 공통코드 상세 피벗 복합키 클래스
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
@EqualsAndHashCode // 복합키는 equals, hashcode 필수 생성
public class ComCodeDPivotId {

  @Column(name = "GRP_CD", length = 45)
  private String grpCd; // 그룹코드

  @Column(name = "DTL_CD", length = 45)
  private String dtlCd; // 상세코드
}
