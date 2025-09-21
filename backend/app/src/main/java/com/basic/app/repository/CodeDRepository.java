package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.ComCodeD;
import com.basic.app.entity.compositeKey.ComCodeDId;
import com.basic.app.entity.compositeKey.ComCodeTId;

/**
 * @파일명 : CodeDRepository.java
 * @설명 : 공통코드 상세 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Repository
public interface CodeDRepository extends JpaRepository<ComCodeD, ComCodeDId> {

  /**
   * @기능 : 그룹 코드로 공통코드 상세 목록을 조회
   * @param grpCd : 그룹 코드
   * @return : 공통코드 상세 목록
   */
  @Query("SELECT c FROM ComCodeD c WHERE c.comCodeDId.comCodeTId.grpCd = ?1 AND c.sts = 'C'")
  List<ComCodeD> findByGrpCd(String grpCd);

  /**
   * @기능 : 공통코드 템플릿 ID로 공통코드 상세 목록을 조회
   * @param comCodeTId : 공통코드 템플릿 복합키
   * @return : 공통코드 상세 목록
   */
  @Query("SELECT c FROM ComCodeD c WHERE c.comCodeDId.comCodeTId = ?1 AND c.sts = 'C'")
  List<ComCodeD> findByComCodeTId(ComCodeTId comCodeTId);

}
