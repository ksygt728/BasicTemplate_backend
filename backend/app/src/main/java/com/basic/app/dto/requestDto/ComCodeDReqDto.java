package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.basic.app.entity.ComCodeD;
import com.basic.app.entity.ComCodeT;
import com.basic.app.entity.compositeKey.ComCodeDId;
import com.basic.app.entity.compositeKey.ComCodeTId;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @파일명 : ComCodeDReqDto.java
 * @설명 : 공통코드 상세 요청 DTO
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
public class ComCodeDReqDto extends BaseReqDto {

  @Schema(description = "그룹코드", example = "GRP001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "그뤂코드는 필수입니다.")
  private String grpCd; // 그룹코드

  @Schema(description = "속성코드", example = "ATTR001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "속성코드는 필수입니다.")
  private String attrCd; // 속성코드

  @Schema(description = "상세코드", example = "DTL001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상세코드는 필수입니다.")
  private String dtlCd; // 상세코드

  @Schema(description = "상세코드명", example = "상세코드이름")
  // @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "상세코드명
  // 필수입니다.")
  private String dtlNm; // 상세코드명

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  @Schema(description = "정렬순서", example = "1")
  @Min(value = 1, groups = { CreateGroup.class, UpdateGroup.class }, message = "정렬순서는 1이상 필수입니다.")
  private int orderNum; // 정렬순서

  /**
   * @기능 : DTO를 Entity로 변환
   * @param dto 공통코드 상세 요청 DTO
   * @return ComCodeD Entity
   */
  public ComCodeD toEntity(ComCodeDReqDto dto) {

    ComCodeTId comCodeTId = new ComCodeTId();
    comCodeTId.setGrpCd(dto.getGrpCd());
    comCodeTId.setAttrCd(dto.getAttrCd());

    ComCodeDId comCodeDId = new ComCodeDId();
    comCodeDId.setComCodeTId(comCodeTId);
    comCodeDId.setDtlCd(dto.getDtlCd());

    return ComCodeD.builder()
        .comCodeDId(comCodeDId)
        .dtlNm(dto.getDtlNm())
        .useYn(dto.getUseYn())
        .orderNum(dto.getOrderNum())
        .build();
  }

}
