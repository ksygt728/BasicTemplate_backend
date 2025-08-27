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
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserReqDto extends BaseReqDto {

  @Schema(description = "사용자 아이디", example = "user123")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자아이디는 필수입니다.")
  private String userId; // 사용자아이디

  @Schema(description = "이름", example = "홍길동")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "이름은 필수입니다.")
  private String name; // 이름

  @Schema(description = "전화번호", example = "010-1234-5678")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "전화번호는 필수입니다.")
  private String phoneNum; // 전화번호

  @Schema(description = "이메일", example = "user123@example.com")
  @Email(groups = { CreateGroup.class, UpdateGroup.class }, message = "유효한 이메일 형식이 아닙니다.")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "이메일은 필수입니다.")
  private String email; // 이메일

  // @Schema(description = "역할", example = "USER")
  // @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "역할은
  // 필수입니다.")
  // private String role; // 역할

  @Schema(description = "사용자타입", example = "NORMAL")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용자타입은 필수입니다.")
  private String userType; // 사용자타입

  @Schema(description = "성별", example = "M", allowableValues = { "M", "F" })
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "성별은 필수입니다.")
  private String gender; // 성별 (M, F)

  @Schema(description = "부서코드", example = "DEPT001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "부서코드는 필수입니다.")
  private String deptCode; // 부서코드

}
