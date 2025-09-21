package com.basic.app.dto.requestDto.baseReqDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * @파일명 : BaseReqDto.java
 * @설명 : BaseReq 요청 DTO
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
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
