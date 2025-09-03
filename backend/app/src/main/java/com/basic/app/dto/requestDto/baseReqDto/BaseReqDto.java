package com.basic.app.dto.requestDto.baseReqDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class BaseReqDto {

  public <T> T toEntity(Class<T> targetClass) {
    ModelMapper modelMapper = new ModelMapper();
    // 전역 매핑 전략을 STRICT로 설정
    modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setSkipNullEnabled(true); // null 값은 무시 (옵션)

    return modelMapper.map(this, targetClass);
  }

}
