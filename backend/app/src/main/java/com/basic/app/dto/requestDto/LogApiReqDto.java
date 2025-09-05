package com.basic.app.dto.requestDto;

import java.time.LocalDateTime;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @파일명 : LogApiReqDto.java
 * @설명 : API 로그 요청 DTO
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
public class LogApiReqDto extends BaseReqDto {

  @Schema(description = "로그아이디", example = "LOG001")
  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String logId; // 로그아이디

  @Schema(description = "사용자 아이디", example = "user123")
  private String userId; // 사용자 아이디

  @Schema(description = "시작시간", example = "2025-08-18 09:00:00")
  private String startDate; // 시작시간

  @Schema(description = "종료시간", example = "2025-08-18 09:10:00")
  private String endDate; // 종료시간

  @Schema(description = "아이피주소", example = "192.168.0.1")
  private String ipAddr; // 아이피주소

  @Schema(description = "브라우저 정보", example = "Mozilla/5.0 ...")
  private String userAgent; // 브라우저 정보

  @Schema(description = "요청 URI", example = "/api/user/login")
  private String requestUri; // 요청 URI

  @Schema(description = "메소드", example = "POST")
  private String httpMethod; // 메소드

  @Schema(description = "요청내용", example = "{\"userId\":\"user123\"}")
  private String requestBody; // 요청내용

  @Schema(description = "응답내용", example = "{\"result\":\"success\"}")
  private String responseBody; // 응답내용

  @Schema(description = "STATUS_CODE", example = "200")
  private String statusCode; // STATUS_CODE

  @Schema(description = "실행시간(ms)", example = "123")
  private long execTime; // 실행시간

}