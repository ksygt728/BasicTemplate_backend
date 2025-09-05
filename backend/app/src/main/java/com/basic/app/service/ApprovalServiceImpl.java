
package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.service.interfaces.ApprovalService;

/**
 * @파일명 : ApprovalServiceImpl.java
 * @설명 : 승인 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class ApprovalServiceImpl implements ApprovalService {

  /**
   * @기능 : 계획된 승인 목록 조회
   * @return 승인 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findPlannedApprovals() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findPlannedApprovals'");
  }

}
