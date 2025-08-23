/**
 * @파일명   : MailMRepository.java
 * @설명     : 스케줄러 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.19
 * @변경이력 :
 *   2025.08.19     김승연       최초 생성
 */
package com.basic.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.SmsH;

@Repository
public interface SmsHRepository extends JpaRepository<SmsH, String> {

  Page<SmsH> findAllBySmsIdAndSts(String smsId, String sts, Pageable pageable);

}
