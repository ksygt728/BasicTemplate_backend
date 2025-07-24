/**
 * @파일명   : ScheMReqDto.java
 * @설명     : 
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.dto.requestDto;

import java.time.LocalDateTime;

import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;

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
public class ScheMReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케쥴아이디는 필수입니다.")
  private String scheId; // 스케줄아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "스케쥴명 필수입니다.")
  private String scheName; // 스케줄명

  private String description; // 설명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "크론식은 필수입니다.")
  private String cronExp; // CRON식

  private LocalDateTime lastExecTime; // 마지막실행시간

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부 (Y,N)

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자

}