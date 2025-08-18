/**
 * @파일명   : InterfaceReqDto.java
 * @설명     : 인터페이스 정보 요청 데이터 전송 객체
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
public class InterfaceReqDto extends BaseReqDto {

  @Schema(description = "인터페이스 아이디", example = "IF001")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "IF아이디는 필수입니다.")
  private String ifId; // 인터페이스 아이디

  @Schema(description = "인터페이스명", example = "회원정보조회인터페이스")
  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "인터페이스명은 필수입니다.")
  private String ifName; // 인터페이스명

  @Schema(description = "WSDL", example = "<wsdl>...</wsdl>")
  private String text; // WSDL

}