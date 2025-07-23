package com.basic.app.entity;

import java.time.LocalDateTime;

import com.basic.app.entity.compositeKey.ComCodeDId;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TB_COM_CODE_D") // 공통코드 상세 테이블
public class ComCodeD {

  @EmbeddedId
  private ComCodeDId comCodeDId;

  // CodeD - CodeT (N:1) [Onwer]
  @MapsId("comCodeTId") // ComCodeDId 클래스 안에 정의된 ComCodeT의 복합키 필드명
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
      @JoinColumn(name = "GRP_CD", referencedColumnName = "GRP_CD"),
      @JoinColumn(name = "ATTR_CD", referencedColumnName = "ATTR_CD")
  })
  private ComCodeT comCodeT;

  @Column(name = "DTL_NM", length = 100, nullable = false)
  private String dtlNm; // 상세코드명

  @Column(name = "USE_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String useYn; // 사용여부 (Y,N)

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

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