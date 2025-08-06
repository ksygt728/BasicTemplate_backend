package com.basic.app.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
@Entity(name = "TB_LOG_API") // API 호출 로그 테이블
public class LogApi extends BaseEntity {
  @Id
  @Column(name = "LOG_ID", length = 36)
  private String logId; // 로그아이디

  @PrePersist
  public void prePersist() {
    if (logId == null) {
      logId = UUID.randomUUID().toString(); // "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
    }
  }

  @Column(name = "USER_ID", length = 45)
  private String userId; // 사용자 아이디

  @Column(name = "IP_ADDR", length = 45)
  private String ipAddr; // 아이피주소

  @Column(name = "USER_AGENT", length = 45)
  private String userAgent; // 브라우저 정보

  @Column(name = "REQUEST_URI", length = 200)
  private String requestUri; // 요청 URI

  @Column(name = "HTTP_METHOD", length = 45)
  private String httpMethod; // 메소드

  @Column(name = "REQUEST_BODY", length = 2048)
  private String requestBody; // 요청내용

  @Column(name = "RESPONSE_BODY", length = 2048)
  private String responseBody; // 응답내용

  @Column(name = "STATUS_CODE", length = 45)
  private String statusCode; // STATUS_CODE

  @Column(name = "EXEC_TIME")
  private LocalDateTime execTime; // 실행시간

}