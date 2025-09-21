package com.basic.app.dto.requestDto;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @파일명 : LogActReqDto.java
 * @설명 : 액션 로그 요청 DTO
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
public class LogActReqDto extends BaseReqDto {

  @Schema(description = "로그아이디", example = "LOG001")
  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String logId; // 로그아이디

  @Schema(description = "사용자 아이디", example = "user123")
  private String userId; // 사용자 아이디

  @Schema(description = "아이피주소", example = "192.168.0.1")
  private String ipAddr; // 아이피주소

  @Schema(description = "페이지URL", example = "/main")
  private String pageUrl; // 페이지URL

  @Schema(description = "액션타입", example = "LOGIN")
  private String actionType; // 액션타입

  @Schema(description = "액션 내용", example = "로그인 시도")
  private String actionTypeDetail; // 액션 내용

}