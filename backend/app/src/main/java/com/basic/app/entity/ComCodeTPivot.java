package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.ComCodeTId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : ComCodeTPivot.java
 * @설명 : 공통코드 속성 피벗 엔티티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_COM_CODE_T_PIVOT") // 공통코드 속성 테이블
public class ComCodeTPivot extends BaseEntity {

  @EmbeddedId
  private ComCodeTId comCodeTId;

  @Column(name = "ATTR_NM", length = 100, nullable = false)
  private String attrNm; // 속성명

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

}