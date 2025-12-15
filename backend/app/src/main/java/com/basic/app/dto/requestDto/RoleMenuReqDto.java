package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.basic.app.entity.RoleMenu;
import com.basic.app.entity.compositeKey.RoleMenuId;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @파일명 : RoleMenuReqDto.java
 * @설명 : 역할별 메뉴 권한 관리를 위한 요청 데이터 전송 객체
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 *       2025.12.14 김승연 menuRw 필드 RW에서 CRUD로 변경
 */
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
  private String roleCd;

  @Schema(description = "메뉴코드", example = "MENU001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴코드는 필수입니다.")
  // RoleMenu - Menu (N:1) [Onwer]
  private String menuCd;

  @Schema(description = "메뉴 접근 수준", example = "R", allowableValues = { "C", "R", "U", "D" })
  // @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴 접근
  // 수준은 필수입니다.")
  private String menuRw; // 메뉴 접근 수준 (C,R,U,D)

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  /**
   * @기능 : DTO를 Entity로 변환
   * @param dto 역할-메뉴 관계 요청 DTO
   * @return RoleMenu Entity
   */
  public RoleMenu toEntity(RoleMenuReqDto dto) {

    RoleMenuId roleMenuId = new RoleMenuId(dto.getRoleCd(), dto.getMenuCd());

    RoleMenu roleMenu = RoleMenu.builder()
        .roleMenuId(roleMenuId) // 복합키는 서비스 레이어에서 설정
        .role(null) // 연관관계 매핑은 서비스 레이어에서 설정
        .menu(null) // 연관관계 매핑은 서비스 레이어에서 설정
        .menuRw(dto.getMenuRw())
        .useYn(dto.getUseYn())
        .build();
    return roleMenu;
  }

}