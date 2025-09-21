package com.basic.app.entity.compositeKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : ComCodeTId.java
 * @설명 : 공통코드 속성 복합키 클래스
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
public class ComCodeTId implements Serializable {

  @Column(name = "GRP_CD", length = 45)
  private String grpCd; // 그룹코드

  @Column(name = "ATTR_CD", length = 45)
  private String attrCd; // 속성코드

}