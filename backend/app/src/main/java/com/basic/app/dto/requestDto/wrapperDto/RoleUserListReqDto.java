package com.basic.app.dto.requestDto.wrapperDto;

import java.util.List;

import com.basic.app.dto.requestDto.RoleUserReqDto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUserListReqDto {

  @Valid // 리스트 내부 요소까지 그룹 적용
  private List<RoleUserReqDto> roleUsers;
}