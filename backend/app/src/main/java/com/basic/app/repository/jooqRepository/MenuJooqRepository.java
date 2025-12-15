
package com.basic.app.repository.jooqRepository;

import java.util.ArrayList;
import java.util.List;

import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.jooq.generated.tables.TbMenu;

/**
 * @파일명 : MenuJooqRepository.java
 * @설명 : JOOQ를 이용한 메뉴 조회폼 조건별 동적 처리 (재귀 CTE 사용)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Repository
@Transactional
public class MenuJooqRepository {

  @Autowired
  private DSLContext dsl;

  /**
   * @기능 : 메뉴 목록 조회 (조건별 동적 쿼리, 재귀 CTE, 페이징)
   * @param menuReqDto 메뉴 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 메뉴 목록 페이지
   */
  public Page<MenuResDto> findAllMenuWithConditions(MenuReqDto menuReqDto, Pageable pageable) {

    TbMenu MENU = TbMenu.TB_MENU;

    /**
     * 1. 트리 재귀 CTE 정의 - 메뉴 계층구조
     */
    CommonTableExpression<?> TB_CTE = DSL.name("CHILDMENUS").as(
        DSL.select(
            MENU.MENU_CD,
            MENU.MENU_NM,
            MENU.UPPER_MENU_CD,
            MENU.MENU_LV,
            MENU.USE_YN,
            MENU.MENU_URL,
            MENU.ORDER_NUM,
            MENU.STS) // STS 추가
            .from(MENU)
            .where(MENU.MENU_CD.eq("MENU00000").and(MENU.STS.eq("C"))) // STS 조건 추가
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
                    MENU.STS) // STS 추가
                    .from(MENU)
                    .join(DSL.table(DSL.name("CHILDMENUS")))
                    .on(MENU.UPPER_MENU_CD.eq(DSL.field(DSL.name("CHILDMENUS", "MENU_CD"), String.class)))
                    .where(MENU.STS.eq("C")))); // STS 조건 추가

    /**
     * 2. 공통 조건 설정
     */

    // CTE 내부 컬럼을 조건으로 사용
    List<Condition> condition = new ArrayList<>();

    if (menuReqDto.getMenuCd() != null && !menuReqDto.getMenuCd().isEmpty()) {
      condition.add(TB_CTE.field("MENU_CD").like("%" + menuReqDto.getMenuCd() + "%"));
    }

    if (menuReqDto.getMenuNm() != null && !menuReqDto.getMenuNm().isEmpty()) {
      condition.add(TB_CTE.field("MENU_NM").like("%" + menuReqDto.getMenuNm() + "%"));
    }

    if (menuReqDto.getMenuLv() != 0) {
      condition.add(TB_CTE.field("MENU_LV", Integer.class).eq(menuReqDto.getMenuLv()));
    }

    if (menuReqDto.getUseYn() != null && !menuReqDto.getUseYn().isEmpty()) {
      condition.add(TB_CTE.field("USE_YN", String.class).eq(menuReqDto.getUseYn()));
    }

    if (menuReqDto.getUpperMenu() != null && !menuReqDto.getUpperMenu().isEmpty()) {
      condition.add(TB_CTE.field("UPPER_MENU_CD").like("%" + menuReqDto.getUpperMenu() + "%"));
    }

    if (menuReqDto.getMenuUrl() != null && !menuReqDto.getMenuUrl().isEmpty()) {
      condition.add(TB_CTE.field("MENU_URL").like("%" + menuReqDto.getMenuUrl() + "%"));
    }

    /**
     * 3. 조건 필터 + 정렬
     */
    List<SortField<?>> sortFields = new ArrayList<>();
    for (Sort.Order order : pageable.getSort()) {
      switch (order.getProperty()) {
        case "menuCd":
          sortFields.add(order.isAscending() ? TB_CTE.field("MENU_CD").asc()
              : TB_CTE.field("MENU_CD").desc());
          break;
        case "menuNm":
          sortFields
              .add(order.isAscending() ? TB_CTE.field("MENU_NM").asc()
                  : TB_CTE.field("MENU_NM").desc());
          break;
        case "menuLv":
          sortFields.add(order.isAscending() ? TB_CTE.field("MENU_LV").asc()
              : TB_CTE.field("MENU_LV").desc());
          break;
        case "orderNum":
          sortFields.add(order.isAscending() ? TB_CTE.field("ORDER_NUM").asc()
              : TB_CTE.field("ORDER_NUM").desc());
          break;
        default:
          // 기본 정렬: 메뉴 레벨 + 정렬순서
          sortFields.add(TB_CTE.field("MENU_LV").asc());
          sortFields.add(TB_CTE.field("ORDER_NUM").asc());
          break;
      }
    }

    // 기본 정렬이 없으면 메뉴 레벨과 정렬순서로 정렬
    if (sortFields.isEmpty()) {
      sortFields.add(TB_CTE.field("MENU_LV").asc());
      sortFields.add(TB_CTE.field("ORDER_NUM").asc());
    }

    /**
     * 4. 페이징 + 결과 추출
     */
    long total = dsl.withRecursive(TB_CTE)
        .selectCount()
        .from(TB_CTE)
        .where(condition) // 조건 처리
        .fetchOne(0, Long.class);

    List<MenuResDto> content = dsl.withRecursive(TB_CTE)
        .select(
            TB_CTE.field("MENU_CD").as("menuCd"),
            TB_CTE.field("MENU_NM").as("menuNm"),
            TB_CTE.field("UPPER_MENU_CD").as("upperMenu"),
            TB_CTE.field("MENU_LV").as("menuLv"),
            TB_CTE.field("USE_YN").as("useYn"),
            TB_CTE.field("MENU_URL").as("menuUrl"),
            TB_CTE.field("ORDER_NUM").as("orderNum"))
        .from(TB_CTE)
        .where(condition) // 조건 처리
        .orderBy(sortFields)
        // .limit(pageable.getPageSize())
        // .offset((int) pageable.getOffset())
        .fetchInto(MenuResDto.class);

    return new PageImpl<>(content, pageable, total);
  }
}
