/**
 * @파일명   : RoleMenuRepository.java
 * @설명     : 권한별 메뉴 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.24
 * @변경이력 :
 *   2025.08.24     김승연       최초 생성
 */
package com.basic.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.RoleMenu;
import com.basic.app.entity.compositeKey.RoleMenuId;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuId> {

}
