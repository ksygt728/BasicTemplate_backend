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
public class RoleMenuReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한코드는 필수입니다.")
  // RoleMenu - Role (N:1) [Onwer]
  private String roldCd;

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴코드는 필수입니다.")
  // RoleMenu - Menu (N:1) [Onwer]
  private String menuCd;

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메뉴 접근 수준은 필수입니다.")
  private String menuRw; // 메뉴 접근 수준 (R,W)

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자
}