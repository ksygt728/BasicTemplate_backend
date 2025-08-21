/**
 * @파일명   : RoleReqDto.java
 * @설명     : 사용자 역할 관리를 위한 요청 데이터 전송 객체
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
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleReqDto {

  @Schema(description = "권한코드", example = "ROLE001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한코드는 필수입니다.")
  private String roldCd; // 권한코드

  @Schema(description = "권한명", example = "관리자")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "권한명은 필수입니다.")
  private String roleName; // 권한명

  @Schema(description = "권한설명", example = "시스템 전체 관리")
  private String roleDesc; // 권한설명

}