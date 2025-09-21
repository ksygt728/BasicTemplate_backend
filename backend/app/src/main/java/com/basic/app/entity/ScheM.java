package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

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
 * @파일명 : ScheM.java
 * @설명 : 스케줄 마스터 엔티티 클래스
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
@Table(name = "TB_SCHE_M") // 스케줄 마스터 테이블
public class ScheM extends BaseEntity {
  @Id
  @Column(name = "SCHE_ID", length = 45)
  private String scheId; // 스케줄아이디

  @Column(name = "SCHE_NAME", length = 100, nullable = false)
  private String scheName; // 스케줄명

  @Column(name = "DESCRIPTION", length = 2048)
  private String description; // 설명

  @Column(name = "SCHE_GROUP", length = 45, nullable = false)
  private String scheGroup; // 스케줄러 그뤂명

  @Column(name = "CLASS_NAME", length = 100, nullable = false)
  private String className; // 클래스명

  @Column(name = "METHOD_NAME", length = 100, nullable = false)
  private String methodName; // 메소드명

  @Column(name = "TRIGGER_NAME", length = 100, nullable = false)
  private String triggerName; // 트리거명

  @Column(name = "CRON_EXP", length = 45, nullable = false)
  private String cronExp; // CRON식

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "LAST_EXEC_TIME", columnDefinition = "TIMESTAMP(3)")
  private LocalDateTime lastExecTime; // 마지막실행시간

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "NEXT_EXEC_TIME", columnDefinition = "TIMESTAMP(3)")
  private LocalDateTime nextExecTime; // 마지막실행시간

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

}