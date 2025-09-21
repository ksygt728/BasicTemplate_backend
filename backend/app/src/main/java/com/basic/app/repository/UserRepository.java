package com.basic.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.User;

/**
 * @파일명 : UserRepository.java
 * @설명 : 유저 정보 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

  /**
   * @기능 : 상태별로 사용자 목록을 페이징 조회
   * @param sts      : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 사용자 목록
   */
  Page<User> findAllBySts(String sts, Pageable pageable);

  /**
   * @기능 : 사용자 ID와 상태로 사용자 정보를 조회
   * @param userId : 사용자 ID
   * @param sts    : 상태 코드
   * @return : 사용자 정보 Optional 객체
   */
  Optional<User> findByUserIdAndSts(String userId, String sts);

  /**
   * @기능 : 이메일로 사용자 정보를 조회
   * @param string : 이메일 주소
   * @return : 사용자 정보 Optional 객체
   */
  Optional<User> findByEmail(String string);

}
