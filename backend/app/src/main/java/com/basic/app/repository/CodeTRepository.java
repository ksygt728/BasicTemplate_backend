package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.ComCodeT;
import com.basic.app.entity.compositeKey.ComCodeTId;

/**
 * @파일명 : CodeTRepository.java
 * @설명 : 공통코드 템플릿 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Repository
public interface CodeTRepository extends JpaRepository<ComCodeT, ComCodeTId> {

  /**
   * @기능 : 그룹 코드로 공통코드 템플릿 목록을 조회
   * @param grpCd : 그룹 코드
   * @return : 공통코드 템플릿 목록
   */
  @Query("SELECT c FROM ComCodeT c WHERE c.comCodeTId.grpCd = ?1 AND c.sts = 'C'")
  List<ComCodeT> findByGrpCd(String grpCd);

}
