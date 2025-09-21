package com.basic.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Bbs;

/**
 * @파일명 : BbsRepository.java
 * @설명 : 게시판 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.09.02
 * @변경이력 :
 *       2025.09.02 김승연 최초 생성
 */
@Repository
public interface BbsRepository extends JpaRepository<Bbs, String> {

}
