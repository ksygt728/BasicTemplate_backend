package com.basic.app.repository.jooqRepository;

import java.util.List;

import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.responseDto.RoleMenuResDto;
import com.basic.app.jooq.generated.tables.TbMenu;
import com.basic.app.jooq.generated.tables.TbRoleMenu;

/**
 * @파일명 : RoleMenuJooqRepository.java
 * @설명 : 권한별 메뉴 트리 조회
 * @작성자 : 김승연
 * @작성일 : 2025.08.27
 * @변경이력 :
 *       2025.08.27 김승연 최초 생성
 */
@Repository
@Transactional
public class RoleMenuJooqRepository {

    @Autowired
    private DSLContext dsl;

    /**
     * @기능 : 특정 권한에 해당하는 메뉴 트리 구조를 재귀 CTE를 이용하여 조회
     * @param roleCd : 권한 코드
     * @return : 계층구조로 정렬된 권한별 메뉴 목록
     */
    public List<RoleMenuResDto> findByMenuTreeWithRole(String roleCd) {

        TbMenu MENU = TbMenu.TB_MENU;
        TbRoleMenu ROLE_MENU = TbRoleMenu.TB_ROLE_MENU;

        /**
         * 1. 트리 재귀 CTE 정의 - 메뉴 계층구조
         */
        CommonTableExpression<?> SUBMENUS_CTE = DSL.name("submenus").as(
                DSL.select(
                        MENU.MENU_CD,
                        MENU.MENU_NM,
                        MENU.UPPER_MENU_CD,
                        MENU.MENU_LV,
                        MENU.USE_YN,
                        MENU.MENU_URL,
                        MENU.ORDER_NUM,
                        MENU.STS)
                        .from(MENU)
                        .where(MENU.MENU_CD.eq("MENU00000")
                                .and(MENU.MENU_LV.eq(0))
                                .and(MENU.STS.eq("C")))
                        .unionAll(
                                // 재귀 케이스
                                DSL.select(
                                        MENU.MENU_CD,
                                        MENU.MENU_NM,
                                        MENU.UPPER_MENU_CD,
                                        MENU.MENU_LV,
                                        MENU.USE_YN,
                                        MENU.MENU_URL,
                                        MENU.ORDER_NUM,
                                        MENU.STS)
                                        .from(MENU)
                                        .join(DSL.table(DSL.name("submenus")))
                                        .on(MENU.UPPER_MENU_CD
                                                .eq(DSL.field(DSL.name("submenus", "MENU_CD"), String.class)))
                                        .where(MENU.STS.eq("C"))));

        /**
         * 2. 메인 쿼리 - CTE와 ROLE_MENU 조인
         */
        List<RoleMenuResDto> result = dsl.withRecursive(SUBMENUS_CTE)
                .select(
                        SUBMENUS_CTE.field("MENU_CD").as("menuCd"),
                        SUBMENUS_CTE.field("MENU_NM").as("menuNm"),
                        SUBMENUS_CTE.field("UPPER_MENU_CD").as("upperMenu"),
                        SUBMENUS_CTE.field("MENU_LV").as("menuLv"),
                        SUBMENUS_CTE.field("MENU_URL").as("menuUrl"),
                        SUBMENUS_CTE.field("ORDER_NUM").as("orderNum"),
                        ROLE_MENU.MENU_RW.as("menuRw"),
                        DSL.when(ROLE_MENU.USE_YN.isNull(), "N")
                                .otherwise(ROLE_MENU.USE_YN).as("useYn"))
                .from(SUBMENUS_CTE)
                .leftJoin(ROLE_MENU)
                .on(SUBMENUS_CTE.field("MENU_CD", String.class).eq(ROLE_MENU.MENU_CD)
                        .and(ROLE_MENU.ROLE_CD.eq(roleCd))
                        .and(ROLE_MENU.STS.eq("C")))
                .where(SUBMENUS_CTE.field("USE_YN", String.class).eq("Y"))
                .orderBy(
                        SUBMENUS_CTE.field("MENU_LV").asc(),
                        SUBMENUS_CTE.field("UPPER_MENU_CD").asc(),
                        SUBMENUS_CTE.field("ORDER_NUM").asc())
                .fetchInto(RoleMenuResDto.class);

        return result;
    }
}
