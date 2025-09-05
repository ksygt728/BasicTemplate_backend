package com.basic.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.SmsM;

/**
 * @파일명 : SmsMRepository.java
 * @설명 : SMS 템플릿 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.19
 * @변경이력 :
 *       2025.08.19 김승연 최초 생성
 */
@Repository
public interface SmsMRepository extends JpaRepository<SmsM, String> {

}
