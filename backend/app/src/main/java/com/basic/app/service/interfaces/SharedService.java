
package com.basic.app.service.interfaces;

import java.util.Map;

/**
 * @파일명 : SharedService.java
 * @설명 : 공통 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface SharedService {

  /**
   * @기능 : 다국어 목록 조회
   * @param localeText 로케일 텍스트
   * @return 다국어 목록 정보가 담긴 Map
   */
  Map<String, Object> getMulLangList(String localeText);

}
