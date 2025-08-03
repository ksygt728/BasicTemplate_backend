/**
 * @파일명   : WebSvcReqDto.java
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
public class WebSvcReqDto extends BaseReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "웹서비스아이디는 필수입니다.")
  private String svcId; // 웹서비스 아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "웹서비스명 필수입니다.")
  private String svcName; // 웹서비스명

}