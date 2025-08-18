package com.basic.app.dto.responseDto.specialDto;

import java.util.ArrayList;
import java.util.List;

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
public class ComCodeInfo {

  private String dtlCd; // 상세코드

  @Builder.Default
  private List<ComCodeAttributesAndValues> codeAttributes = new ArrayList<>(); // 속성 정보

  private String useYn; // 사용여부 (Y,N)

  private int dtlOrderNum; // 정렬순서
}
