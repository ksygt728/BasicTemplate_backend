/**
 * @파일명   : RoleUserReqDto.java
 * @설명     : 
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
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUserReqDto extends BaseReqDto {

  @Schema(description = "권한코드", example = "ROLE001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한코드는 필수입니다.")
  private String roldCd;

  @Schema(description = "사용자아이디", example = "user123")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자아이디는 필수입니다.")
  private String userId;

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

}