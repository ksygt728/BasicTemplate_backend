package com.basic.app.entity;

import com.basic.app.entity.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "TB_LOG_ACT") // 사용자 행위 로그 테이블
public class LogAct extends BaseEntity {
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

}