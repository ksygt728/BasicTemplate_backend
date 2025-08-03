/**
 * @파일명   : LogApiReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */

package com.basic.app.dto.requestDto;

import java.time.LocalDateTime;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.requestDto.baseReqDto.BaseReqDto;

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
public class LogApiReqDto extends BaseReqDto {

  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String logId; // 로그아이디

  private String userId; // 사용자 아이디

  private String ipAddr; // 아이피주소

  private String userAgent; // 브라우저 정보

  private String requestUri; // 요청 URI

  private String httpMethod; // 메소드

  private String requestBody; // 요청내용

  private String responseBody; // 응답내용

  private String statusCode; // STATUS_CODE

  private LocalDateTime execTime; // 실행시간

}