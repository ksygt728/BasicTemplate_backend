package com.basic.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.basic.app.dto.responseDto.ComCodeTResDto;
import com.basic.app.entity.baseEntity.BaseEntity;
import com.basic.app.entity.compositeKey.ComCodeTId;
import com.basic.app.util.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "TB_COM_CODE_T") // 공통코드 속성 테이블
public class ComCodeT extends BaseEntity {

  @EmbeddedId
  private ComCodeTId comCodeTId;

  // CodeT - CodeM (N:1) [Onwer]
  @MapsId("grpCd")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GRP_CD")
  private ComCodeM comCodeM;

  @Column(name = "ATTR_NM", length = 100, nullable = false)
  private String attrNm; // 속성명

  @Column(name = "ORDER_NUM", nullable = false)
  private int orderNum; // 정렬순서

  // CodeT - CodeD (1:N)
  @OrderBy("orderNum ASC")
  @OneToMany(mappedBy = "comCodeT", fetch = FetchType.LAZY)
  private List<ComCodeD> comCodeDs = new ArrayList<ComCodeD>(); // 속성코드에 포함된 상세코드 리스트

  public ComCodeTResDto toDto(ComCodeT entity) {
    return ComCodeTResDto.builder()
        .attrCd(entity.getComCodeTId().getAttrCd())
        .attrNm(entity.getAttrNm())
        .orderNum(entity.getOrderNum())
        .comCodeDs(
            entity.getComCodeDs().stream()
                .filter(target -> target.getSts().equals(Status.POSITIVE) && target.getUseYn().equals("Y"))
                .map(target -> target.toDto(target))
                .toList())
        .build();
  }

}