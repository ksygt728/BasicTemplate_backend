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
public class ScheHReqDto {

  @NotBlank(groups = { CreateGroup.class }, message = "로그아이디는 필수입니다.")
  private String logId; // 사용자이력ID

  private String scheId; // 스케줄아이디

  private String startTime; // 시작시간

  private String endTime; // 종료시간

  private String execTime; // 실행시간

  private String success; // 성공여부

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자
}