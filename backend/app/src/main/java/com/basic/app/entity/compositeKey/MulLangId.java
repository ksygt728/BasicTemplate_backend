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
 * @파일명 : MulLangId.java
 * @설명 : 다국어 정보 복합키 클래스
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
public class MulLangId implements Serializable {

  @Column(name = "LANG_CD", length = 45)
  private String langCd; // 언어코드

  @Column(name = "LANG_TYPE", length = 45, nullable = false)
  private String langType; // 언어유형

  @Column(name = "LANG_GUBUN", length = 45, nullable = false)
  private String langGubun; // 언어구분(code, msg, label, etc)

}