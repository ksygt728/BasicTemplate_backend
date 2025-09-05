
package com.basic.app.service.interfaces;

import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.entity.ScheM;

/**
 * @파일명 : SchedulerService.java
 * @설명 : 스케줄러 관련 서비스 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.08.6
 * @변경이력 :
 *       2025.08.6 김승연 최초 생성
 */
public interface SchedulerService {

  /**
   * @기능 : 스케줄러 초기화
   */
  void init();

  /**
   * @기능 : 특정 스케줄러 초기화
   * @param scheM 스케줄러 엔티티
   */
  void init(ScheM scheM);

  /**
   * @기능 : 관리자용 스케줄러 전체 목록 조회 (페이징)
   * @param scheMReqDto 스케줄러 검색 조건 DTO
   * @param pageable    페이징 정보
   * @return 스케줄러 목록 정보가 담긴 Map
   */
  Map<String, Object> findAllSchedulerForAdmin(ScheMReqDto scheMReqDto, Pageable pageable);

  /**
   * @기능 : 관리자용 특정 스케줄러 상세 조회
   * @param scheId 스케줄러 ID
   * @return 스케줄러 상세 정보가 담긴 Map
   */
  Map<String, Object> findBySchedulerForAdmin(String scheId);

  /**
   * @기능 : 관리자용 스케줄러 실행 이력 조회 (페이징)
   * @param scheId   스케줄러 ID
   * @param pageable 페이징 정보
   * @return 스케줄러 실행 이력 정보가 담긴 Map
   */
  Map<String, Object> findBySchedulerHistoryForAdmin(String scheId, Pageable pageable);

  /**
   * @기능 : 관리자용 스케줄러 등록
   * @param scheMDto 스케줄러 등록 요청 DTO
   * @return 등록 결과 정보가 담긴 Map
   */
  Map<String, Object> insertSchedulerForAdmin(ScheMReqDto scheMDto);

  /**
   * @기능 : 관리자용 스케줄러 수정
   * @param scheMDto 스케줄러 수정 요청 DTO
   * @return 수정 결과 정보가 담긴 Map
   */
  Map<String, Object> updateSchedulerForAdmin(ScheMReqDto scheMDto);

  /**
   * @기능 : 관리자용 스케줄러 실행
   * @param scheId 스케줄러 ID
   * @return 실행 결과 정보가 담긴 Map
   */
  Map<String, Object> executeSchedulerForAdmin(String scheId);

  /**
   * @기능 : 관리자용 스케줄러 삭제
   * @param scheId 스케줄러 ID
   * @return 삭제 결과 정보가 담긴 Map
   */
  Map<String, Object> deleteSchedulerForAdmin(String scheId);

}
