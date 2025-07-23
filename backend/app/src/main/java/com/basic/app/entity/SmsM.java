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
@Entity(name = "TB_SMS_M") // SMS 템플릿 테이블
public class SmsM {
  @Id
  @Column(name = "SMS_ID", length = 45)
  private String smsId; // SMS 아이디

  @Column(name = "LANG_TYPE", length = 45, nullable = false)
  private String langType; // 언어타입

  @Column(name = "SMS_NAME", length = 100, nullable = false)
  private String smsName; // 템플릿명

  @Column(name = "TEXT", length = 200, nullable = false)
  private String text; // SMS내용

  @Column(name = "DESCRIPTION", length = 2048)
  private String description; // 설명

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