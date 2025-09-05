
package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.ChaebunReqDto;

/**
 * @파일명 : ChaebunService.java
 * @설명 : 채번 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface ChaebunService {

  /**
   * @기능 : 관리자용 채번 설정 전체 목록 조회 (페이징)
   * @param chaebunReqDto 채번 검색 조건 DTO
   * @param pageable      페이징 정보
   * @return 채번 설정 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllChaebunForAdmin(ChaebunReqDto chaebunReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 채번 설정 상세 조회
   * @param seqId 채번 ID
   * @return 채번 설정 상세 정보가 담긴 Map
   */
  Map<String, Object> findByChaebunForAdmin(String seqId);

  /**
   * @기능 : 관리자용 채번 설정 등록
   * @param chaebunReqDto 채번 설정 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertChaebunForAdmin(ChaebunReqDto chaebunReqDto);

  /**
   * @기능 : 관리자용 채번 설정 수정
   * @param chaebunReqDto 채번 설정 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateChaebunForAdmin(ChaebunReqDto chaebunReqDto);

  /**
   * @기능 : 관리자용 채번 설정 삭제
   * @param seqId 채번 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteChaebunForAdmin(String seqId);

  /**
   * @기능 : 시퀀스 번호 생성
   * @param seqId 채번 ID
   * @return 생성된 시퀀스 번호
   */
  String generateSeq(String seqId);
}
