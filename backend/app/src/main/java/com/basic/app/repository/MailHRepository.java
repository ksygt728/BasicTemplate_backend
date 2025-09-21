package com.basic.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.MailH;

/**
 * @파일명 : MailHRepository.java
 * @설명 : 메일 발송 이력 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.19
 * @변경이력 :
 *       2025.08.19 김승연 최초 생성
 */
@Repository
public interface MailHRepository extends JpaRepository<MailH, String> {

  /**
   * @기능 : 상태와 성공 여부로 최신 100건의 메일 이력을 생성일 오름차순으로 조회
   * @param sts     : 상태 코드
   * @param success : 성공 여부
   * @return : 메일 이력 목록 (최대 100건)
   */
  List<MailH> findTop100ByStsAndSuccessOrderByCreateDateAsc(String sts, String success);

  /**
   * @기능 : 메일 ID와 상태로 메일 이력을 페이징 조회
   * @param mailId   : 메일 ID
   * @param positive : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 메일 이력 목록
   */
  Page<MailH> findAllByMailIdAndSts(String mailId, String positive, Pageable pageable);

}
