package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.service.interfaces.SequenceService;

/**
 * @파일명 : SequenceServiceImpl.java
 * @설명 : 시퀀스 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class SequenceServiceImpl implements SequenceService {

  /**
   * @기능 : 관리자용 시퀀스 전체 목록 조회
   * @return 시퀀스 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllSequenceForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllSequenceForAdmin'");
  }

  /**
   * @기능 : 관리자용 특정 시퀀스 상세 조회
   * @param seqId 시퀀스 ID
   * @return 시퀀스 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findBySequenceForAdmin(String seqId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBySequenceForAdmin'");
  }

  /**
   * @기능 : 관리자용 시퀀스 신규 등록
   * @param chaebun 시퀀스 등록 요청 DTO
   * @return 등록된 시퀀스 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> insertSequenceForAdmin(ChaebunReqDto chaebun) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertSequenceForAdmin'");
  }

  /**
   * @기능 : 관리자용 시퀀스 정보 수정
   * @param chaebun 시퀀스 수정 요청 DTO
   * @return 수정된 시퀀스 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> updateSequenceForAdmin(ChaebunReqDto chaebun) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSequenceForAdmin'");
  }

  /**
   * @기능 : 관리자용 시퀀스 삭제
   * @param seqId 삭제할 시퀀스 ID
   * @return 삭제 성공 메시지가 담긴 Map
   */
  @Override
  public Map<String, Object> deleteSequenceForAdmin(String seqId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteSequenceForAdmin'");
  }

}
