package com.basic.app.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_SCHE_H") // 스케줄 실행 이력 테이블
public class ScheH extends BaseEntity {
  @Id
  @Column(name = "LOG_ID", length = 36)
  private String logId; // 사용자이력ID

  @PrePersist
  public void prePersist() {
    if (logId == null) {
      logId = UUID.randomUUID().toString(); // "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
    }
  }

  @Column(name = "SCHE_ID", length = 45)
  private String scheId; // 스케줄아이디

  @Column(name = "SCHE_GROUP", length = 45)
  private String scheGroup; // 스케줄러 그뤂명

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "START_TIME", columnDefinition = "TIMESTAMP(3)")
  private LocalDateTime startTime; // 시작시간

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "END_TIME", columnDefinition = "TIMESTAMP(3)")
  private LocalDateTime endTime; // 종료시간

  @Column(name = "EXEC_TIME", length = 2048)
  private long execTime; // 실행시간

  @Column(name = "SUCCESS", length = 1)
  private String success; // 성공여부

  @Column(name = "ERROR_MSG", columnDefinition = "TEXT")
  private String errorMsg; // 실패사유

}