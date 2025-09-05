package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.basic.app.entity.ComCodeT;
import com.basic.app.entity.RoleUser;
import com.basic.app.entity.compositeKey.ComCodeTId;
import com.basic.app.entity.compositeKey.RoleUserId;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : RoleUserReqDto.java
 * @설명 : 사용자-역할 관계 요청 DTO
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
public class RoleUserReqDto extends BaseReqDto {

  @Schema(description = "권한코드", example = "ROLE001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한코드는 필수입니다.")
  private String roleCd;

  @Schema(description = "사용자아이디", example = "user123")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자아이디는 필수입니다.")
  private String userId;

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  /**
   * @기능 : DTO를 Entity로 변환
   * @param dto 사용자-역할 관계 요청 DTO
   * @return RoleUser Entity
   */
  public RoleUser toEntity(RoleUserReqDto dto) {

    RoleUserId roleUserId = new RoleUserId();
    roleUserId.setRoleCd(dto.getRoleCd());
    roleUserId.setUserId(dto.getUserId());

    return RoleUser.builder()
        .roleUserId(roleUserId)
        .useYn(dto.getUseYn())
        .build();
  }

}