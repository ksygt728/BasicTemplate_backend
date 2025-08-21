/**
 * @파일명   : ScheHReqDto.java
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

import jakarta.persistence.Column;
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
public class ScheHReqDto extends BaseReqDto {

  @Schema(description = "로그아이디", example = "LOG001")
  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String logId; // 사용자이력ID

  @Schema(description = "스케줄아이디", example = "SCH001")
  private String scheId; // 스케줄아이디

  @Schema(description = "스케줄러 그룹명", example = "DEFAULT")
  private String scheGroup; // 스케줄러 그뤂명

  @Schema(description = "시작시간", example = "2025-08-18 T09:00:00")
  private String startTime; // 시작시간

  @Schema(description = "종료시간", example = "2025-08-1 8T10:00:00")
  private String endTime; // 종료시간

  @Schema(description = "성공여부", example = "Y", allowableValues = { "Y", "N" })
  private String success; // 성공여부

}