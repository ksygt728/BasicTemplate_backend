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
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.basic.app.entity.MulLang;
import com.basic.app.entity.compositeKey.MulLangId;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MulLangReqDto extends BaseReqDto {

  @Schema(description = "언어코드", example = "ko")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어코드는 필수입니다.")
  private String langCd; // 언어코드

  @Schema(description = "언어유형", example = "KR")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어유형은 필수입니다.")
  private String langType; // 언어유형

  @Schema(description = "언어명", example = "한국어")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어명은 필수입니다.")
  private String langNm; // 언어명

  @Schema(description = "언어구분", example = "공용")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "언어구분은 필수입니다.")
  private String langGubun; // 언어구분

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  public MulLang toEntity(MulLangReqDto dto) {

    return MulLang.builder()
        .mulLangId(MulLangId.builder()
            .langCd(dto.getLangCd())
            .langType(dto.getLangType())
            .langGubun(dto.getLangGubun())
            .build()) // 기본키는 서비스 레이어에서 설정
        .langNm(dto.getLangNm())
        .useYn(dto.getUseYn())
        .build();

  }

}