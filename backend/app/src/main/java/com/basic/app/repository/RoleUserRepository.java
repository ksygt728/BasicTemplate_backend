package com.basic.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.RoleUser;
import com.basic.app.entity.compositeKey.RoleUserId;

/**
 * @파일명 : RoleUserRepository.java
 * @설명 : 사용자별 권한 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.24
 * @변경이력 :
 *       2025.08.24 김승연 최초 생성
 */
@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserId> {

  /**
   * @기능 : 사용자 ID와 상태로 사용자별 권한 목록을 페이징 조회
   * @param userId   : 사용자 ID
   * @param sts      : 상태 코드
   * @param pageable : 페이징 정보
   * @return : 페이징된 사용자별 권한 목록
   */
  Page<RoleUser> findByRoleUserIdUserIdAndSts(String userId, String sts, Pageable pageable);

  /**
   * @기능 : 사용자 ID와 상태로 사용자별 권한 목록을 전체 조회
   * @param userId : 사용자 ID
   * @param sts    : 상태 코드
   * @return : 사용자별 권한 목록
   */
  List<RoleUser> findByRoleUserIdUserIdAndSts(String userId, String sts);

  /**
   * @기능 : 권한 코드와 상태로 권한별 사용자 목록을 조회
   * @param roleCd : 권한 코드
   * @param sts    : 상태 코드
   * @return : 권한별 사용자 목록
   */
  List<RoleUser> findByRoleUserIdRoleCdAndSts(String roleCd, String sts);

}
