package com.basic.app.entity;

import com.basic.app.dto.responseDto.MulLangResDto;
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

/**
 * @파일명 : MulLang.java
 * @설명 : 다국어 정보 엔티티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
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
  private MulLangId mulLangId; // 복합키: 언어코드, 언어유형

  @Column(name = "LANG_NM", length = 2048, nullable = false)
  private String langNm; // 언어명

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  /**
   * @기능 : Entity를 DTO로 변환
   * @param entity 변환할 MulLang 엔티티
   * @return 변환된 MulLangResDto 객체
   */
  public MulLangResDto toDto(MulLang entity) {
    return MulLangResDto.builder()
        .langCd(entity.getMulLangId().getLangCd())
        .langType(entity.getMulLangId().getLangType())
        .langGubun(entity.getMulLangId().getLangGubun())
        .langNm(entity.getLangNm())
        .useYn(entity.getUseYn())
        .build();

  }

}