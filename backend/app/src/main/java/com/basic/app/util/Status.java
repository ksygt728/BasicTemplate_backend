
package com.basic.app.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @파일명 : Status.java
 * @설명 : 상태 코드 정의 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.22
 * @변경이력 :
 *       2025.07.22 김승연 최초 생성
 */
@Getter
@RequiredArgsConstructor
public class Status {

  public static final String POSITIVE = "C"; // 활성 상태
  public static final String NAGATIVE = "D"; // 비활성 상태

}