/**
 * @파일명   : ScheMReqDto.java
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
public class ScheMReqDto extends BaseReqDto {

  @Schema(description = "스케줄아이디", example = "SCH001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케줄아이디는 필수입니다.")
  private String scheId; // 스케줄아이디

  @Schema(description = "스케줄명", example = "정기점검")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케줄명 필수입니다.")
  private String scheName; // 스케줄명

  @Schema(description = "설명", example = "스케줄 설명")
  private String description; // 설명

  @Schema(description = "스케줄러 그룹명", example = "DEFAULT")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케줄러 그뤂명은 필수입니다.")
  private String scheGroup; // 스케줄러 그뤂명

  @Schema(description = "클래스명", example = "com.basic.app.jobs.SampleJob")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "클래스명은 필수입니다.")
  private String className; // 클래스명

  @Schema(description = "메소드명", example = "execute")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메소드명은 필수입니다.")
  private String methodName; // 메소드명

  @Schema(description = "트리거명", example = "TRG001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "트리거명은 필수입니다.")
  private String triggerName; // 트리거명

  @Schema(description = "CRON식", example = "0 0 12 * * ?")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "크론식은 필수입니다.")
  private String cronExp; // CRON식

  @Schema(description = "사용여부", example = "Y", allowableValues = { "Y", "N" })
  private String useYn; // 사용여부 (Y,N)

}