/**
 * @파일명   : ComCodeMResDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.basic.app.dto.requestDto.ComCodeTReqDto;

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
public class ComCodeMResDto {
  private String grpCd; // 그룹코드

  private String grpCdType; // 그룹코드유형

  private String grpNm; // 그룹코드명

  private String sts; // 시스템 상태 (C, D)

  private String createUser; // 생성자

  private LocalDateTime createDate; // 생성일

  private String updateUser; // 수정자

  private LocalDateTime timestamp; // 수정일

  // CodeM - CodeT (1:N)
  private List<ComCodeTReqDto> comCodeTs = new ArrayList<ComCodeTReqDto>(); // 그뤂코드에 포함된 그뤂코드속성 리스트

}