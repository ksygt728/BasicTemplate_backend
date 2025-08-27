/**
 * @파일명   : MenuRepository.java
 * @설명     : 메뉴 레포지토리
 * @작성자   : 김승연
 * @작성일   : 2025.08.24
 * @변경이력 :
 *   2025.08.24     김승연       최초 생성
 */
package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

  @Modifying
  @Query(value = """
      WITH RECURSIVE submenus AS (
          SELECT MENU_CD
          FROM TB_MENU
          WHERE MENU_CD = ?1 AND STS = 'C'
          UNION ALL
          SELECT m.MENU_CD
          FROM TB_MENU m
          INNER JOIN submenus sm ON m.UPPER_MENU_CD = sm.MENU_CD
          WHERE m.STS = 'C'
      )
      UPDATE TB_MENU t
      JOIN submenus s ON t.MENU_CD = s.MENU_CD
      SET t.STS = ?2
      """, nativeQuery = true)
  int deleteMenuAndSubmenus(String menuCd, String sts);

  // @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.subMenus WHERE m.upperMenu IS
  // NULL")
  // @EntityGraph(attributePaths = "subMenus")
  List<Menu> findAllByStsOrderByMenuLvAscOrderNumAsc(String sts);
}
