/**
 * @파일명   : RoleUserRepository.java
 * @설명     : 사용자별 권한 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.24
 * @변경이력 :
 *   2025.08.24     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.RoleUser;
import com.basic.app.entity.compositeKey.RoleUserId;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserId> {

  Page<RoleUser> findByRoleUserIdUserIdAndSts(String userId, String sts, Pageable pageable);

  List<RoleUser> findByRoleUserIdUserIdAndSts(String userId, String sts);

  List<RoleUser> findByRoleUserIdRoleCdAndSts(String roleCd, String sts);

}
