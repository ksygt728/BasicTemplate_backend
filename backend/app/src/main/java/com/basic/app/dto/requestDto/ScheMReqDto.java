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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheMReqDto extends BaseReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케줄아이디는 필수입니다.")
  private String scheId; // 스케줄아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케줄명 필수입니다.")
  private String scheName; // 스케줄명

  private String description; // 설명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케줄러 그뤂명은 필수입니다.")
  private String scheGroup; // 스케줄러 그뤂명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "클래스명은 필수입니다.")
  private String className; // 클래스명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "메소드명은 필수입니다.")
  private String methodName; // 메소드명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "트리거명은 필수입니다.")
  private String triggerName; // 트리거명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "크론식은 필수입니다.")
  private String cronExp; // CRON식

  private String useYn; // 사용여부 (Y,N)

}