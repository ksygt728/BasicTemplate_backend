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
@Entity(name = "TB_MUL_LANG") // 다국어 테이블
public class MulLang {
  @Id
  @Column(name = "LANG_CD", length = 45)
  private String langCd; // 언어코드

  @Column(name = "LANG_TYPE", length = 45, nullable = false)
  private String langType; // 언어유형

  @Column(name = "LANG_NM", length = 2048, nullable = false)
  private String langNm; // 언어명

  @Column(name = "LANG_GUBUN", length = 45, nullable = false)
  private String langGubun; // 언어구분

  @Column(name = "USE_YN", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "STS", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'C'")
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