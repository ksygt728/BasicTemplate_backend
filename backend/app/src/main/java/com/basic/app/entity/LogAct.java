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
@Entity(name = "TB_LOG_ACT") // 사용자 행위 로그 테이블
public class LogAct {
  @Id
  @Column(name = "LOG_ID", length = 45)
  private String logId; // 로그아이디

  @Column(name = "USER_ID", length = 45)
  private String userId; // 사용자 아이디

  @Column(name = "IP_ADDR", length = 45)
  private String ipAddr; // 아이피주소

  @Column(name = "PAGE_URL", length = 200)
  private String pageUrl; // 페이지URL

  @Column(name = "ACTION_TYPE", length = 45)
  private String actionType; // 액션타입

  @Column(name = "ACTION_TYPE_DETAIL", length = 45)
  private String actionTypeDetail; // 액션 내용

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