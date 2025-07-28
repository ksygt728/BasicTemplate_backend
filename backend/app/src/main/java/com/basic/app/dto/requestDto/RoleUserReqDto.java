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
public class RoleUserReqDto {

  // RoleUser - Role (N:1) [Onwer]
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한코드는 필수입니다.")
  private String roldCd;

  // RoleUser - User (N:1) [Onwer]
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자아이디는 필수입니다.")
  private String userId;

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

}