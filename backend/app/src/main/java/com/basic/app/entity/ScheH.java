package com.basic.app.entity;

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
@Entity(name = "TB_SCHE_H") // 스케줄 실행 이력 테이블
public class ScheH extends BaseEntity {
  @Id
  @Column(name = "LOG_ID", length = 45)
  private String logId; // 사용자이력ID

  @Column(name = "SCHE_ID", length = 45)
  private String scheId; // 스케줄아이디

  @Column(name = "START_TIME", length = 45)
  private String startTime; // 시작시간

  @Column(name = "END_TIME", length = 45)
  private String endTime; // 종료시간

  @Column(name = "EXEC_TIME", length = 2048)
  private String execTime; // 실행시간

  @Column(name = "SUCCESS", length = 1)
  private String success; // 성공여부

  @Column(name = "ERROR_MSG", length = 2048)
  private String errorMsg; // 실패사유

}