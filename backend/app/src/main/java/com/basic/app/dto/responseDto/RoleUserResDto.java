
package com.basic.app.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @파일명 : RoleUserResDto.java
 * @설명 : 사용자-역할 관계 응답 DTO
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
public class RoleUserResDto {

  // RoleUser - Role (N:1) [Onwer]

  // RoleUser - User (N:1) [Onwer]
  private String userId;

  private String useYn; // 사용여부 (Y,N)

  private RoleResDto role;
}