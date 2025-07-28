/**
 * @파일명   : ChaebunReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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
public class ChaebunReqDto {

  @NotBlank(groups = { UpdateGroup.class }, message = "채번아이디는 필수입니다.")
  private String seqId; // 채번아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번명을 입력하세요.")
  private String seqName; // 채번명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번고유번호 누락")
  private String prefix; // 채번고유번호

  @Null(groups = { CreateGroup.class, UpdateGroup.class }, message = "현재 채번값이 입력되었습니다.")
  private int currentValue; // 현재 채번값

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "증가량을 입력하세요.")
  private int step; // 증가량

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번길이를 입력하세요.")
  private int length; // 채번길이

  private String dateformat; // 데이터포맷

}