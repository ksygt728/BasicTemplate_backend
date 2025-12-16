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
 * @파일명 : LogErrorReqDto.java
 * @설명 : 에러 로그 요청 DTO
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 *       2025.12.16 김승연 createDate 필드 추가
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogErrorReqDto extends BaseReqDto {

  @Schema(description = "로그아이디", example = "ERR001")
  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String errId; // 로그아이디

  @Schema(description = "사용자 아이디", example = "user123")
  private String userId; // 사용자 아이디

  @Schema(description = "아이피주소", example = "192.168.0.1")
  private String ipAddr; // 아이피주소

  @Schema(description = "브라우저 정보", example = "Mozilla/5.0 ...")
  private String userAgent; // 브라우저 정보

  @Schema(description = "요청 URI", example = "/api/user/login")
  private String requestUri; // 요청 URI

  @Schema(description = "메소드", example = "POST")
  private String httpMethod; // 메소드

  @Schema(description = "에러내용", example = "NullPointerException")
  private String errMsg; // 에러내용

  @Schema(description = "에러내용상세", example = "java.lang.NullPointerException at ...")
  private String errStack; // 에러내용상세

  @Schema(description = "시작시간", example = "2025-08-18 09:00:00")
  private String createDate; // 시작시간

}