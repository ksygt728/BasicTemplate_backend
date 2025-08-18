/**
 * @파일명   : ComCodeDResDto.java
 * @설명     : 공통 코드 상세 정보 응답 데이터 전송 객체
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.responseDto;

import java.time.LocalDateTime;

import com.basic.app.entity.compositeKey.ComCodeDId;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.MapsId;
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
public class ComCodeDResDto {

  // private ComCodeDId comCodeDId;

  private String dtlCd; // 상세코드

  private String dtlNm; // 상세코드명

  private String useYn; // 사용여부 (Y,N)

  private int orderNum; // 정렬순서

}