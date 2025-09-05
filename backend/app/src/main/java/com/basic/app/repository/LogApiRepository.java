package com.basic.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.LogApi;

/**
 * @파일명 : LogApiRepository.java
 * @설명 : API 로그 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Repository
public interface LogApiRepository extends JpaRepository<LogApi, String> {

}
