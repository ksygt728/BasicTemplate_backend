package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.MulLang;
import com.basic.app.entity.compositeKey.MulLangId;

/**
 * @파일명 : MulLangRepository.java
 * @설명 : 다국어 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.27
 * @변경이력 :
 *       2025.08.27 김승연 최초 생성
 */
@Repository
public interface MulLangRepository extends JpaRepository<MulLang, MulLangId> {

  /**
   * @기능 : 언어 구분과 언어 코드로 다국어 목록을 조회
   * @param langGubun : 언어 구분
   * @param langCd    : 언어 코드
   * @return : 다국어 목록
   */
  List<MulLang> findByMulLangIdLangGubunAndMulLangIdLangCd(String langGubun, String langCd);

}
