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
public class GroupCodeInfo {

  private String grpCdType; // 그룹코드유형

  private String grpCd; // 그룹코드

  private String grpNm; // 그룹코드명

  // @Builder.Default
  // private Map<String, ComCodeInfo> comCodeInfo = new LinkedHashMap<>(); // 각각 행

  @Builder.Default
  private List<ComCodeInfo> comCodeInfo = new ArrayList<>(); // 각각 행

}
