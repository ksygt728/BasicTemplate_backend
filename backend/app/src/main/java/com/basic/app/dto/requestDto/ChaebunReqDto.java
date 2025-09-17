package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @파일명 : ChaebunReqDto.java
 * @설명 : 채번 요청 DTO
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
public class ChaebunReqDto extends BaseReqDto {

  @Schema(description = "채번아이디", example = "SEQ001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번아이디는 필수입니다.")
  private String seqId; // 채번아이디

  @Schema(description = "채번명", example = "회원번호채번")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번명을 입력하세요.")
  private String seqName; // 채번명

  @Schema(description = "채번패턴", example = "{PREFIX}_{yyyyMMdd}_{0001}")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번패턴을 입력하세요.")
  private String pattern; // 채번패턴

  @Schema(description = "채번고유번호", example = "PRFX001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번고유번호 누락")
  private String prefix; // 채번고유번호

  // @Schema(description = "현재 채번값", example = "* 시스템에서 자동으로 주기때문에 별도 기입 불필요")
  // // @Null(groups = { CreateGroup.class, UpdateGroup.class }, message = "현재
  // 채번값이
  // // 입력되었습니다.")
  // private int currentValue; // 현재 채번값

  @Schema(description = "증가량", example = "1")
  @NotNull(groups = { CreateGroup.class, UpdateGroup.class }, message = "증가량을 입력하세요.")
  @Min(value = 1, groups = { CreateGroup.class, UpdateGroup.class }, message = "증가량은 1 이상이어야 합니다.")
  private int step; // 증가량

  @Schema(description = "채번길이", example = "8")
  @NotNull(groups = { CreateGroup.class, UpdateGroup.class }, message = "채번길이를 입력하세요.")
  @Min(value = 1, groups = { CreateGroup.class, UpdateGroup.class }, message = "채번길이는 1 이상이어야 합니다.")
  private int length; // 채번길이

  @Schema(description = "데이터포맷", example = "yyyyMMdd")
  private String dateformat; // 데이터포맷

}