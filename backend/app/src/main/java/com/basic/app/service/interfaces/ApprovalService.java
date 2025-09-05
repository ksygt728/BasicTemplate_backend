
package com.basic.app.service.interfaces;

import java.util.Map;

/**
 * @파일명 : ApprovalService.java
 * @설명 : 승인 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface ApprovalService {

  /**
   * @기능 : 계획된 승인 목록 조회
   * @return 승인 목록 정보가 담긴 Map
   */
  Map<String, Object> findPlannedApprovals();

}
