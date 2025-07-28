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
@Entity(name = "TB_SMS_M") // SMS 템플릿 테이블
public class SmsM extends BaseEntity {
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

}