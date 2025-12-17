package com.basic.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.basic.app.entity.RoleMenu;
import com.basic.app.entity.compositeKey.RoleMenuId;

/**
 * @파일명 : RoleMenuRepository.java
 * @설명 : 권한별 메뉴 레포지토리
 * @작성자 : 김승연
 * @작성일 : 2025.08.24
 * @변경이력 :
 *       2025.08.24 김승연 최초 생성
 *       2025.12.15 deleteRoleMenuListContainsMenuCd,
 *       deleteRoleMenuListContainsRoleCd 메서드 추가(특정 권한을 가진 모든 사용자 권한논리 삭제)
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuId> {

    /**
     * @기능 : 특정 메뉴에 해당하는 모든 권한 메뉴를 논리 삭제
     * @param menuCd : 권한 코드
     */

    @Modifying
    @Query("UPDATE RoleMenu rm SET rm.sts = 'D', rm.useYn = 'N' WHERE rm.roleMenuId.menuCd = ?1")
    void deleteRoleMenuListContainsMenuCd(String menuCd);

    /**
     * @기능 : 특정 권한에 해당하는 모든 권한 메뉴를 논리 삭제
     * @param roleCd : 권한 코드
     */

    @Modifying
    @Query("UPDATE RoleMenu rm SET rm.sts = 'D', rm.useYn = 'N' WHERE rm.roleMenuId.roleCd = ?1")
    void deleteRoleMenuListContainsRoleCd(String roleCd);

    // public interface RoleMenuResDto {
    // String getMenuCd();

    // String getMenuNm();

    // String getUpperMenuCd();

    // Integer getMenuLv();

    // String getMenuUrl();

    // Integer getOrderNum();

    // String getMenuRw();

    // String getUseYn();
    // }

    // @Query(value = """
    // WITH RECURSIVE submenus AS (
    // SELECT
    // MENU_CD,
    // MENU_NM,
    // UPPER_MENU_CD,
    // MENU_LV,
    // USE_YN,
    // MENU_URL,
    // ORDER_NUM,
    // STS
    // FROM TB_MENU
    // WHERE UPPER_MENU_CD = 'ROOT' AND STS = 'C'
    // UNION ALL
    // SELECT
    // m.MENU_CD,
    // m.MENU_NM,
    // m.UPPER_MENU_CD,
    // m.MENU_LV,
    // m.USE_YN,
    // m.MENU_URL,
    // m.ORDER_NUM,
    // m.STS
    // FROM TB_MENU m
    // INNER JOIN submenus sm ON m.UPPER_MENU_CD = sm.MENU_CD
    // WHERE m.STS = 'C'
    // )
    // SELECT
    // sm.MENU_CD as 'menuCd',
    // sm.MENU_NM as 'menuNm',
    // sm.UPPER_MENU_CD as 'upperMenuCd',
    // sm.MENU_LV as 'menuLv',
    // sm.MENU_URL as 'menuUrl',
    // sm.ORDER_NUM as 'orderNum',
    // rm.MENU_RW as 'menuRw',
    // CASE WHEN rm.USE_YN IS NULL THEN 'N' ELSE rm.USE_YN END AS 'useYn'
    // FROM submenus sm
    // LEFT JOIN TB_ROLE_MENU rm
    // ON sm.MENU_CD = rm.MENU_CD
    // AND rm.ROLE_CD = ?1 AND rm.STS = 'C'
    // WHERE sm.USE_YN = 'Y'
    // ORDER BY sm.MENU_LV, sm.UPPER_MENU_CD, sm.ORDER_NUM
    // """, nativeQuery = true)
    // List<RoleMenuResDto> findByMenuTreeWithRole(String roleCd);

}
