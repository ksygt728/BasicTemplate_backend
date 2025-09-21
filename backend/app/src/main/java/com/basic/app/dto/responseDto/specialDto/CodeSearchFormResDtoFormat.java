package com.basic.app.dto.responseDto.specialDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : CodeSearchFormResDtoFormat.java
 * @설명 : 공통 코드 검색 결과 응답 데이터 전송 객체
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSearchFormResDtoFormat {

  private String grpCdType; // 그룹코드유형

  private String grpCd; // 그룹코드

  private String grpNm; // 그룹코드명

  private ComCodeInfo comCodeInfo;

}