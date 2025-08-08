package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TB_SCHE_M") // 스케줄 마스터 테이블
public class ScheM extends BaseEntity {
  @Id
  @Column(name = "SCHE_ID", length = 45)
  private String scheId; // 스케줄아이디

  @Column(name = "SCHE_NAME", length = 100, nullable = false)
  private String scheName; // 스케줄명

  @Column(name = "DESCRIPTION", length = 2048)
  private String description; // 설명

  @Column(name = "CRON_EXP", length = 45, nullable = false)
  private String cronExp; // CRON식

  @Column(name = "LAST_EXEC_TIME")
  private LocalDateTime lastExecTime; // 마지막실행시간

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

}