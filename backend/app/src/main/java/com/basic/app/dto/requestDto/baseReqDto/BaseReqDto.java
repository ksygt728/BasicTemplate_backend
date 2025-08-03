package com.basic.app.dto.requestDto.baseReqDto;

import org.modelmapper.ModelMapper;

public class BaseReqDto {

  public <T> T toEntity(Class<T> targetClass) {
    return new ModelMapper().map(this, targetClass);
  }

}
