package com.basic.app.entity;

import java.util.UUID;

import com.basic.app.entity.baseEntity.BaseEntity;

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

/**
 * @파일명 : SmsH.java
 * @설명 : SMS 발송 이력 엔티티 클래스
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
@Table(name = "TB_SMS_H") // SMS 발송 이력 테이블
public class SmsH extends BaseEntity {
  @Id
  @Column(name = "LOG_ID", length = 45)
  private String logId; // 사용자이력ID

  /**
   * @기능 : 엔티티 저장 전 ID 자동 생성
   */
  @PrePersist
  public void prePersist() {
    if (logId == null) {
      logId = UUID.randomUUID().toString(); // "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
    }
  }

  @Column(name = "SMS_ID", length = 45)
  private String smsId; // SMS아이디

  @Column(name = "FROM_PHONE", length = 45)
  private String fromPhone; // 발신자

  @Column(name = "TO_PHONE", length = 45)
  private String toPhone; // 수신자

  @Column(name = "TEXT", length = 200)
  private String text; // 내용

  @Column(name = "SUCCESS", length = 1)
  private String success; // 성공여부

  @Column(name = "ERROR_MSG", length = 2048)
  private String errorMsg; // 실패사유

}