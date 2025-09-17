package com.basic.app.dto.requestDto.wrapperDto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.basic.app.dto.requestDto.RoleMenuReqDto;

@Getter
@Setter
public class RoleMenuListReqDto {

  @Valid // 리스트 내부 요소까지 그룹 적용
  private List<RoleMenuReqDto> roleMenus;
}