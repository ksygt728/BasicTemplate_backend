package com.basic.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.Menu;

/**
 * @파일명 : MenuRepository.java
 * @설명 : 메뉴 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.24
 * @변경이력 :
 *       2025.08.24 김승연 최초 생성
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

  /**
   * @기능 : 재귀 CTE를 이용하여 메뉴와 하위 메뉴들의 상태를 일괄 업데이트
   * @param menuCd : 대상 메뉴 코드
   * @param sts    : 변경할 상태 값
   * @return : 업데이트된 행 수
   */
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

  /**
   * @기능 : 상태별로 메뉴를 계층 순서와 정렬 번호 순으로 조회
   * @param sts : 상태 코드
   * @return : 정렬된 메뉴 목록
   */
  // @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.subMenus WHERE m.upperMenu IS
  // NULL")
  // @EntityGraph(attributePaths = "subMenus")
  List<Menu> findAllByStsOrderByMenuLvAscOrderNumAsc(String sts);
}
