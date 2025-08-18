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