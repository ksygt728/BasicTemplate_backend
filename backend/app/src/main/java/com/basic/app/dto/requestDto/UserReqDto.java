/**
 * @파일명   : UserReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
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
public class UserReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자아이디는 필수입니다.")
  private String userId; // 사용자아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "비밀번호는 필수입니다.")
  private String password; // 비밀번호

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "이름은 필수입니다.")
  private String name; // 이름

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "전화번호는 필수입니다.")
  private String phoneNum; // 전화번호

  @Email
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "이메일은 필수입니다.")
  private String email; // 이메일

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "역할은 필수입니다.")
  private String role; // 역할

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자타입은 필수입니다.")
  private String userType; // 사용자타입

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "성별은 필수입니다.")
  private String gender; // 성별 (M, F)

  // User - Department (N:1) [Onwer]
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서코드는 필수입니다.")
  private String deptCode; // 부서코드

}
