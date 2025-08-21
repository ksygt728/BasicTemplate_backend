/**
 * @파일명   : RoleMenuReqDto.java
 * @설명     : 역할별 메뉴 권한 관리를 위한 요청 데이터 전송 객체
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
public class RoleMenuReqDto extends BaseReqDto {

  @Schema(description = "권한코드", example = "ROLE001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한코드는 필수입니다.")
  // RoleMenu - Role (N:1) [Onwer]
  private String roldCd;

  @Schema(description = "메뉴코드", example = "MENU001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴코드는 필수입니다.")
  // RoleMenu - Menu (N:1) [Onwer]
  private String menuCd;

  @Schema(description = "메뉴 접근 수준", example = "R", allowableValues = { "R", "W" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴 접근 수준은 필수입니다.")
  private String menuRw; // 메뉴 접근 수준 (R,W)

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

}