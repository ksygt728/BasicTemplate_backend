
package com.basic.app.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @파일명 : XConverter.java
 * @설명 : XConverter 클래스는 다양한 Object <> JSON or 문자열등으로 변환하는 유틸리티 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.08.08
 * @변경이력 :
 *       2025.08.08 김승연 최초 생성
 */

@Component
@Getter
@Setter
@NoArgsConstructor
public class XConverter {

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * @기능 : AOP에서 사용하는 Args를 JSON으로 변환 (password 필드 마스킹 처리)
   * @param args 변환할 메소드 인자 배열
   * @return JSON 문자열
   */
  public String convertArgsToJson(Object[] args) {
    try {
      Object[] maskedArgs = new Object[args.length];
      for (int i = 0; i < args.length; i++) {
        Object arg = args[i];
        if (arg == null) {
          maskedArgs[i] = null;
          continue;
        }
        Class<?> clazz = arg.getClass();

        // ✅ Spring Data 관련 객체들은 문자열로 변환
        if (clazz.getName().contains("org.springframework.data")) {
          maskedArgs[i] = convertPageableToMap(arg);
          continue;
        }

        // ✅ Pageable, PageRequest 등은 상세 정보로 변환
        if (clazz.getName().contains("Pageable") || clazz.getName().contains("PageRequest")) {
          maskedArgs[i] = convertPageableToMap(arg);
          continue;
        }

        // DTO나 Map 등만 마스킹 시도
        if (!clazz.getName().startsWith("java.")) {
          try {
            Object clone = objectMapper.convertValue(arg, clazz);
            for (Field field : clazz.getDeclaredFields()) {
              if ("password".equalsIgnoreCase(field.getName())) {
                field.setAccessible(true);
                field.set(clone, "****");
              }
            }
            maskedArgs[i] = clone;
          } catch (Exception e) {
            // 직렬화 실패 시 toString()으로 fallback
            maskedArgs[i] = arg.toString();
          }
        } else {
          maskedArgs[i] = arg;
        }
      }
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maskedArgs);
    } catch (Exception e) {
      e.printStackTrace();
      return "[Unserializable request params]";
    }
  }

  /**
   * @기능 : AOP에서 사용하는 Object를 JSON으로 변환
   * @param obj 변환할 객체
   * @return JSON 문자열
   */
  public String convertObjectToJson(Object obj) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      e.printStackTrace();
      return "[Unserializable response]";
    }
  }

  /**
   * @기능 : Pageable 객체를 읽기 쉬운 Map으로 변환
   * @param pageableObj 변환할 Pageable 객체
   * @return 페이징 정보가 담긴 Map
   */
  public Map<String, Object> convertPageableToMap(Object pageableObj) {
    Map<String, Object> pageableInfo = new HashMap<>();

    try {
      if (pageableObj instanceof Pageable) {
        Pageable pageable = (Pageable) pageableObj;

        pageableInfo.put("page", pageable.getPageNumber());
        pageableInfo.put("size", pageable.getPageSize());

        // 정렬 정보 추가
        if (pageable.getSort() != null && pageable.getSort().isSorted()) {
          Map<String, String> sortInfo = new HashMap<>();
          pageable.getSort().forEach(order -> {
            sortInfo.put(order.getProperty(), order.getDirection().toString());
          });
          pageableInfo.put("sort", sortInfo);
        } else {
          pageableInfo.put("sort", "UNSORTED");
        }
      } else {
        // Pageable이 아닌 경우 toString() 사용
        pageableInfo.put("info", pageableObj.toString());
      }
    } catch (Exception e) {
      pageableInfo.put("error", "Failed to parse Pageable: " + e.getMessage());
      pageableInfo.put("toString", pageableObj.toString());
    }

    return pageableInfo;
  }

}
