/**
 * @파일명   : MulLangRepository.java
 * @설명     : 다국어 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.27
 * @변경이력 :
 *   2025.08.27     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.MulLang;
import com.basic.app.entity.compositeKey.MulLangId;

@Repository
public interface MulLangRepository extends JpaRepository<MulLang, MulLangId> {

  List<MulLang> findByMulLangIdLangGubunAndMulLangIdLangCd(String langGubun, String langCd);

}
