package com.basic.app.api;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

/**
 * @파일명 : ModelMapperUtils.java
 * @설명 : DTO <> Entity 변환 유틸리티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class ModelMapperUtils {

  private static final ModelMapper modelMapper = new ModelMapper();

  static {
    // 정적 블록에서 초기화 시 전략 변경
    modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT) // STRICT 전략 적용
        .setSkipNullEnabled(true) // null 값은 무시 (옵션)
        .setFieldMatchingEnabled(true) // 필드 기반 매핑 허용
    ;
  }

  /**
   * @기능 : 단일 객체 변환
   * @param source      변환할 소스 객체
   * @param targetClass 변환할 대상 클래스 타입
   * @return 변환된 대상 객체
   */
  public static <S, T> T map(S source, Class<T> targetClass) {
    return modelMapper.map(source, targetClass);
  }

  /**
   * @기능 : List 변환
   * @param source      변환할 소스 리스트
   * @param targetClass 변환할 대상 클래스 타입
   * @return 변환된 대상 리스트
   */
  public static <S, T> List<T> map(List<S> source, Class<T> targetClass) {
    return source.stream()
        .map(element -> modelMapper.map(element, targetClass))
        .collect(Collectors.toList());
  }

  /**
   * @기능 : Page -> PageResponse 변환 (동적쿼리 타입 JOOQ 사용 시)
   * @param source 변환할 소스 Page 객체
   * @return 변환된 PageResponse 객체
   */
  public static <S> PageResponse<S> map(Page<S> source) {
    return new PageResponse<>(source);
  }

  /**
   * @기능 : Entity -> DTO 변환 & Page -> PageResponse 변환
   * @param source      변환할 소스 Page 객체
   * @param targetClass 변환할 대상 클래스 타입
   * @return 변환된 PageResponse 객체
   */
  public static <S, T> PageResponse<T> map(Page<S> source, Class<T> targetClass) {
    Page<T> page = source.map(entity -> modelMapper.map(entity, targetClass));
    return new PageResponse<>(page);
  }
}