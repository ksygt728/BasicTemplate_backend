/**
 * @파일명   : MailHRepository.java
 * @설명     : 스케줄러 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.19
 * @변경이력 :
 *   2025.08.19     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.MailH;

@Repository
public interface MailHRepository extends JpaRepository<MailH, String> {

  List<MailH> findTop100ByStsAndSuccessOrderByCreateDateAsc(String sts, String success);

}
