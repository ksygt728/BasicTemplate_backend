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
@Entity(name = "TB_CHAEBUN") // 채번(시퀀스) 테이블
public class Chaebun {

  @Id
  @Column(name = "SEQ_ID", length = 45)
  private String seqId; // 채번아이디

  @Column(name = "SEQ_NAME", length = 100, nullable = false)
  private String seqName; // 채번명

  @Column(name = "PREFIX", length = 45, nullable = false)
  private String prefix; // 채번고유번호

  @Column(name = "CURRENT_VALUE", nullable = false)
  private int currentValue; // 현재 채번값

  @Column(name = "STEP", nullable = false)
  private int step; // 증가량

  @Column(name = "LENGTH", nullable = false)
  private int length; // 채번길이

  @Column(name = "DATEFORMAT", length = 45)
  private String dateformat; // 데이터포맷

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