/**
 * @파일명   : AuthReqDto.java
 * @설명     : 로그인폼에 사용하는 DTO
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto.specialDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true) // JwtAuthenticationFilter.java 에서 jackson ObejctMapper null값이 들어가는것을 무시
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthReqDto extends BaseReqDto {

  @NotBlank(groups = { CreateGroup.class }, message = "사용자아이디는 필수입니다.")
  private String userId; // 사용자아이디

  @NotBlank(groups = { CreateGroup.class }, message = "비밀번호는 필수입니다.")
  private String password; // 비밀번호

}
