/**
 * @파일명   : ModelMapperUtils.java
 * @설명     : DTO <> Entity 변환 유틸리티 클래스
 * @작성자   : 김승연
 * @작성일   : 2025.07.24
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */

package com.basic.app.mapping;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class ModelMapperUtils {

  private static final ModelMapper modelMapper = new ModelMapper();

  // 단일 객체 변환
  public static <S, T> T map(S source, Class<T> targetClass) {
    return modelMapper.map(source, targetClass);
  }

  // List 변환
  public static <S, T> List<T> map(List<S> source, Class<T> targetClass) {
    return source.stream()
        .map(element -> modelMapper.map(element, targetClass))
        .collect(Collectors.toList());
  }

  // Page 변환
  public static <S, T> Page<T> map(Page<S> source, Class<T> targetClass) {
    return source.map(entity -> modelMapper.map(entity, targetClass));
  }
}