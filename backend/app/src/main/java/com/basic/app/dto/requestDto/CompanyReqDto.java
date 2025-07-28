/**
 * @파일명   : CompanyReqDto.java
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
public class CompanyReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "회사코드는 필수입니다.")
  private String companyCode; // 회사코드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "회사코드명은 필수입니다.")
  private String companyName; // 회사명

}