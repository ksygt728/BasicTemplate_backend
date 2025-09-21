
package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.ChaebunReqDto;

/**
 * @파일명 : SequenceService.java
 * @설명 : 시퀀스 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface SequenceService {

  /**
   * @기능 : 관리자용 시퀀스 전체 목록 조회
   * @return 시퀀스 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllSequenceForAdmin();

  /**
   * @기능 : 관리자용 특정 시퀀스 상세 조회
   * @param seqId 시퀀스 ID
   * @return 시퀀스 상세 정보가 담긴 Map
   */
  Map<String, Object> findBySequenceForAdmin(String seqId);

  /**
   * @기능 : 관리자용 시퀀스 등록
   * @param chaebun 채번 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertSequenceForAdmin(ChaebunReqDto chaebun);

  /**
   * @기능 : 관리자용 시퀀스 수정
   * @param chaebun 채번 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateSequenceForAdmin(ChaebunReqDto chaebun);

  /**
   * @기능 : 관리자용 시퀀스 삭제
   * @param seqId 시퀀스 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteSequenceForAdmin(String seqId);

}
