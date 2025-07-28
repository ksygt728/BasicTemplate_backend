/**
 * @파일명   : MulLangReqDto.java
 * @설명     : 다국어 처리를 위한 요청 데이터 전송 객체
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;

import jakarta.validation.constraints.NotBlank;
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
public class MulLangReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어코드는 필수입니다.")
  private String langCd; // 언어코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어유형은 필수입니다.")
  private String langType; // 언어유형

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어명은 필수입니다.")
  private String langNm; // 언어명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어구분은 필수입니다.")
  private String langGubun; // 언어구분

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

}