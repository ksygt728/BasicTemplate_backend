/**
 * @파일명   : MenuReqDto.java
 * @설명     : 메뉴 관리를 위한 요청 데이터 전송 객체
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

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
public class MenuReqDto extends BaseReqDto {

  @NotBlank(groups = { UpdateGroup.class }, message = "메뉴코드는 필수입니다.")
  private String menuCd; // 메뉴코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴명은 필수입니다.")
  private String menuNm; // 메뉴명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상위메뉴코드는 필수입니다.")
  private String upperMenuCd; // 상위메뉴코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴레벨은 필수입니다.")
  private int menuLv; // 메뉴레벨

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  private String menuUrl; // 메뉴 URL

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "정렬순서는 필수입니다.")
  private int orderNum; // 정렬순서

}