package com.basic.app.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TB_SCHE_M") // 스케줄 마스터 테이블
public class ScheM {
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

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "STS", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'C'")
  private String sts; // 시스템 상태 (C, D)

  @Column(name = "CREATE_USER", length = 45, nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'SYSTEM'")
  private String createUser; // 생성자

  @Column(name = "CREATE_DATE", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createDate; // 생성일

  @Column(name = "UPDATE_USER", length = 45, nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'SYSTEM'")
  private String updateUser; // 수정자

  @Column(name = "TIMESTAMP", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime timestamp; // 수정일
}