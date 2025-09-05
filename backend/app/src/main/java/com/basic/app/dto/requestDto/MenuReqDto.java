package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : MenuReqDto.java
 * @설명 : 메뉴 관리를 위한 요청 데이터 전송 객체
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
public class MenuReqDto extends BaseReqDto {

  @Schema(description = "메뉴코드", example = "MENU001")
  @NotBlank(groups = { UpdateGroup.class }, message = "메뉴코드는 필수입니다.")
  private String menuCd; // 메뉴코드

  @Schema(description = "메뉴명", example = "대시보드")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴명은 필수입니다.")
  private String menuNm; // 메뉴명

  @Schema(description = "상위메뉴코드", example = "MENU000")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상위메뉴코드는 필수입니다.")
  private String upperMenu; // 상위메뉴코드

  @Schema(description = "메뉴레벨", example = "1")
  @NotNull(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴레벨은 필수입니다.")
  private int menuLv; // 메뉴레벨

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  @Schema(description = "메뉴 URL", example = "/dashboard")
  private String menuUrl; // 메뉴 URL

  @Schema(description = "정렬순서", example = "1")
  @NotNull(groups = { CreateGroup.class, UpdateGroup.class }, message = "정렬순서는 필수입니다.")
  @Min(value = 1, groups = { CreateGroup.class, UpdateGroup.class }, message = "정렬순서는 최소 1 이상이어야 합니다.")
  private int orderNum; // 정렬순서

}