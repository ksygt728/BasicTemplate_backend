package com.basic.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.SmsH;

/**
 * @파일명 : SmsHRepository.java
 * @설명 : SMS 발송 이력 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.19
 * @변경이력 :
 *       2025.08.19 김승연 최초 생성
 */
@Repository
public interface SmsHRepository extends JpaRepository<SmsH, String> {

  /**
   * @기능 : SMS ID와 상태로 SMS 발송 이력을 페이징 조회
   * @param smsId    : SMS ID
   * @param sts      : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 SMS 발송 이력 목록
   */
  Page<SmsH> findAllBySmsIdAndSts(String smsId, String sts, Pageable pageable);

}
