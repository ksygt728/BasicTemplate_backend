package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Interface;

/**
 * @파일명 : InterfaceRepository.java
 * @설명 : 인터페이스 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Repository
public interface InterfaceRepository extends JpaRepository<Interface, String> {

  /**
   * @기능 : 상태별로 인터페이스 목록을 페이징 조회
   * @param string   : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 인터페이스 목록
   */
  Page<Interface> findAllBySts(String string, Pageable pageable);

  /**
   * @기능 : 인터페이스 ID와 상태로 인터페이스 정보를 조회
   * @param ifId : 인터페이스 ID
   * @param sts  : 상태 코드
   * @return : 인터페이스 정보 Optional 객체
   */
  Optional<Interface> findByIfIdAndSts(String ifId, String sts);

}
