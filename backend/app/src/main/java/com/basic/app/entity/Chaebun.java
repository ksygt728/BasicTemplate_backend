package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : Chaebun.java
 * @설명 : 채번(시퀀스) 엔티티 클래스
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
@Table(name = "TB_CHAEBUN") // 채번(시퀀스) 테이블
public class Chaebun extends BaseEntity {

  @Id
  @Column(name = "SEQ_ID", length = 45)
  private String seqId; // 채번아이디

  @Column(name = "SEQ_NAME", length = 100, nullable = false)
  private String seqName; // 채번명

  @Column(name = "PATTERN", length = 45, nullable = false)
  private String pattern; // 채번패턴

  @Column(name = "PREFIX", length = 45, nullable = false)
  private String prefix; // 채번고유번호

  @Column(name = "CURRENT_VALUE", nullable = false)
  private int currentValue; // 현재 채번값

  @Column(name = "STEP", nullable = false, columnDefinition = "int default 1")
  private int step; // 증가량

  @Column(name = "LENGTH", nullable = false, columnDefinition = "int default 4")
  private int length; // 채번길이

  @Column(name = "DATEFORMAT", length = 45)
  private String dateformat; // 데이터포맷

}