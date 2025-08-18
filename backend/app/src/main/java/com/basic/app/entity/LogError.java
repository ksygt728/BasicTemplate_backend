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
@Entity
@Table(name = "TB_LOG_ERROR") // 에러 로그 테이블
public class LogError extends BaseEntity {

  @Id
  @Column(name = "ERR_ID", length = 36)
  private String errId;

  @PrePersist
  public void prePersist() {
    if (errId == null) {
      errId = UUID.randomUUID().toString(); // "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
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

  @Column(name = "ERR_MSG", length = 2048)
  private String errMsg; // 에러내용

  @Column(name = "ERR_STACK", length = 65535)
  private String errStack; // 에러내용상세

}