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
public class InterfaceReqDto {

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "IF아이디는 필수입니다.")
  private String ifId; // 인터페이스 아이디

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "인터페이스명은 필수입니다.")
  private String ifName; // 인터페이스명

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "인터페이스 유형은 필수입니다.")
  private String ifType; // 인터페이스 유형

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "인터페이스 URL은 필수입니다.")
  private String ifUrl; // 인터페이스 URL

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "HTTP 메소드는 필수입니다.")
  private String ifMethod; // HTTP 메소드

  @NotBlank(groups = { CreateGroup.class, UpdateGroup.class }, message = "사용여부는 필수입니다.")
  private String useYn; // 사용여부

  @NotBlank(groups = { CreateGroup.class }, message = "생성자는 필수입니다.")
  private String createUser; // 생성자

  @NotBlank(groups = { UpdateGroup.class }, message = "수정자는 필수입니다.")
  private String updateUser; // 수정자
}