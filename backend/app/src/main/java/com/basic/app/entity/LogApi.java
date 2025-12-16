package com.basic.app.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.basic.app.entity.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
 * @파일명 : LogApi.java
 * @설명 : API 호출 로그 엔티티 클래스
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
@Table(name = "TB_LOG_API", indexes = {
    @Index(name = "idx_access_log_end_date", columnList = "END_DATE"),
    @Index(name = "idx_access_log_user_end_date", columnList = "USER_ID, END_DATE"),
    @Index(name = "idx_access_log_uri_end_date", columnList = "REQUEST_URI, END_DATE"),
    @Index(name = "idx_access_log_ip_end_date", columnList = "IP_ADDR, END_DATE")
}) // API 호출 로그 테이블
public class LogApi extends BaseEntity {
  @Id
  @Column(name = "LOG_ID", length = 36)
  private String logId; // 로그아이디

  /**
   * @기능 : 엔티티 저장 전 ID 자동 생성
   */
  @PrePersist
  public void prePersist() {
    if (logId == null) {
      logId = UUID.randomUUID().toString(); // "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
    }
  }

  @Column(name = "USER_ID", length = 45)
  private String userId; // 사용자 아이디

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "START_DATE", columnDefinition = "TIMESTAMP(3)")
  private LocalDateTime startDate; // 시작시간

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @Column(name = "END_DATE", columnDefinition = "TIMESTAMP(3)")
  private LocalDateTime endDate; // 종료시간

  @Column(name = "IP_ADDR", length = 45)
  private String ipAddr; // 아이피주소

  @Column(name = "USER_AGENT", length = 200)
  private String userAgent; // 브라우저 정보

  @Column(name = "REQUEST_URI", length = 200)
  private String requestUri; // 요청 URI

  @Column(name = "HTTP_METHOD", length = 45)
  private String httpMethod; // 메소드

  @Column(name = "REQUEST_BODY", columnDefinition = "TEXT")
  private String requestBody; // 요청내용

  @Column(name = "RESPONSE_BODY", columnDefinition = "TEXT")
  private String responseBody; // 응답내용

  @Column(name = "STATUS_CODE", length = 45)
  private String statusCode; // STATUS_CODE

  @Column(name = "EXEC_TIME")
  private long execTime; // 실행시간

}