/**
 * @파일명   : UserRepository.java
 * @설명     : 유저 정보 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.07.31
 * @변경이력 :
 *   2025.07.31     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.ComCodeT;
import com.basic.app.entity.compositeKey.ComCodeTId;

@Repository
public interface CodeTRepository extends JpaRepository<ComCodeT, ComCodeTId> {

  @Query("SELECT c FROM ComCodeT c WHERE c.comCodeTId.grpCd = ?1 AND c.sts = 'C'")
  List<ComCodeT> findByGrpCd(String grpCd);

}
