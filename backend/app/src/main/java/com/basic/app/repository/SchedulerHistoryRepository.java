package com.basic.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.ScheH;

/**
 * @파일명 : SchedulerHistoryRepository.java
 * @설명 : 스케줄러 이력 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.09
 * @변경이력 :
 *       2025.08.09 김승연 최초 생성
 */
@Repository
public interface SchedulerHistoryRepository extends JpaRepository<ScheH, String> {

  /**
   * @기능 : 스케줄러 ID와 상태로 스케줄러 이력을 페이징 조회
   * @param scheId   : 스케줄러 ID
   * @param positive : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 스케줄러 이력 목록
   */
  Page<ScheH> findAllByScheIdAndSts(String scheId, String positive, Pageable pageable);

}
