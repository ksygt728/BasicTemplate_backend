/**
 * @파일명   : SmsHReqDto.java
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
public class SmsHReqDto extends BaseReqDto {

  @Schema(description = "로그아이디", example = "LOG001")
  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String logId; // 사용자이력ID

  @Schema(description = "SMS아이디", example = "SMS001")
  private String smsId; // SMS아이디

  @Schema(description = "발신자", example = "01012345678")
  private String fromPhone; // 발신자

  @Schema(description = "수신자", example = "01098765432")
  private String toPhone; // 수신자

  @Schema(description = "내용", example = "인증번호는 1234입니다.")
  private String text; // 내용

  @Schema(description = "성공여부", example = "Y", allowableValues = { "Y", "N" })
  private String success; // 성공여부

  @Schema(description = "실패사유", example = "SMS 전송 실패")
  private String errorMsg; // 실패사유

}