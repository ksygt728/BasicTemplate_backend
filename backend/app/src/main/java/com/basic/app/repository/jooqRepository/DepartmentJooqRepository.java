
package com.basic.app.repository.jooqRepository;

import java.util.ArrayList;
import java.util.List;

import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.DepartmentReqDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.entity.jooq.tables.TbCompany;
import com.basic.app.entity.jooq.tables.TbDepartment;

/**
 * @파일명 : DepartmentJooqRepository.java
 * @설명 : JOOQ를 이용한 부서 조회폼 조건별 동적 처리 (재귀 CTE 사용)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */

@Repository
@Transactional
public class DepartmentJooqRepository {

  @Autowired
  private DSLContext dsl;

  /**
   * @기능 : 부서 목록 조회 (조건별 동적 쿼리, 재귀 CTE, 페이징)
   * @param departmentReqDto 부서 검색 조건 DTO
   * @param pageable         페이징 정보
   * @return 부서 목록 페이지
   */
  public Page<DepartmentResDto> findAllDepartmentWithConditions(DepartmentReqDto departmentReqDto, Pageable pageable) {

    TbDepartment DEPARTMENT = TbDepartment.TB_DEPARTMENT;
    TbCompany COMPANY = TbCompany.TB_COMPANY;

    /**
     * 1. 트리 재귀 CTE 정의 - 수정
     */
    CommonTableExpression<?> TB_CTE = DSL.name("CHILDDEPTS").as(
        DSL.select(
            DEPARTMENT.DEPT_CODE,
            DEPARTMENT.DEPT_NM,
            DEPARTMENT.UPPER_DEPT_CODE,
            DEPARTMENT.DEPT_LV,
            DEPARTMENT.COMPANY_CODE,
            DEPARTMENT.USE_YN,
            DEPARTMENT.STS) // STS 추가
            .from(DEPARTMENT)
            .where(DEPARTMENT.UPPER_DEPT_CODE.eq("ROOT").and(DEPARTMENT.STS.eq("C"))) // STS 조건 추가
            .unionAll(
                // 재귀 케이스
                DSL.select(
                    DEPARTMENT.DEPT_CODE,
                    DEPARTMENT.DEPT_NM,
                    DEPARTMENT.UPPER_DEPT_CODE,
                    DEPARTMENT.DEPT_LV,
                    DEPARTMENT.COMPANY_CODE,
                    DEPARTMENT.USE_YN,
                    DEPARTMENT.STS) // STS 추가
                    .from(DEPARTMENT)
                    .join(DSL.table(DSL.name("CHILDDEPTS")))
                    .on(DEPARTMENT.UPPER_DEPT_CODE.eq(DSL.field(DSL.name("CHILDDEPTS", "DEPT_CODE"), String.class)))
                    .where(DEPARTMENT.STS.eq("C")))); // STS 조건 추가

    /**
     * 2. 공통 조건 설정
     */

    // CTE 내부 컬럼을 조건으로 사용
    List<Condition> condition = new ArrayList<>();

    if (departmentReqDto.getDeptCode() != null && !departmentReqDto.getDeptCode().isEmpty()) {
      condition.add(TB_CTE.field("DEPT_CODE").like("%" + departmentReqDto.getDeptCode() + "%"));
    }

    if (departmentReqDto.getDeptNm() != null && !departmentReqDto.getDeptNm().isEmpty()) {
      condition.add(TB_CTE.field("DEPT_NM").like("%" + departmentReqDto.getDeptNm() + "%"));
    }

    if (departmentReqDto.getDeptLv() != 0) {
      condition.add(TB_CTE.field("DEPT_LV", Integer.class).eq(departmentReqDto.getDeptLv()));
    }

    if (departmentReqDto.getUseYn() != null && !departmentReqDto.getUseYn().isEmpty()) {
      condition.add(TB_CTE.field("USE_YN", String.class).eq(departmentReqDto.getUseYn()));
    }

    if (departmentReqDto.getCompanyCode() != null && !departmentReqDto.getCompanyCode().isEmpty()) {
      condition.add(TB_CTE.field("COMPANY_CODE").like("%" + departmentReqDto.getCompanyCode() + "%"));
    }

    /**
     * 3. 조건 필터 + 정렬
     */
    List<SortField<?>> sortFields = new ArrayList<>();
    for (Sort.Order order : pageable.getSort()) {
      switch (order.getProperty()) {
        case "deptCode":
          sortFields.add(order.isAscending() ? TB_CTE.field("DEPT_CODE").asc()
              : TB_CTE.field("DEPT_CODE").desc());
          break;
        case "deptNm":
          sortFields
              .add(order.isAscending() ? TB_CTE.field("DEPT_NM").asc()
                  : TB_CTE.field("DEPT_NM").desc());
          break;
        case "deptLv":
          sortFields.add(order.isAscending() ? TB_CTE.field("DEPT_LV").asc()
              : TB_CTE.field("DEPT_LV").desc());
          break;
        default:
          break;
      }
    }

    /**
     * 4. 페이징 + 결과 추출 - 수정
     */
    long total = dsl.withRecursive(TB_CTE)
        .selectCount()
        .from(TB_CTE)
        .innerJoin(COMPANY)
        .on(
            TB_CTE.field("COMPANY_CODE", String.class).eq(COMPANY.COMPANY_CODE)
                .and(COMPANY.STS.eq("C")))
        .where(condition) // 조건 처리 수정
        .fetchOne(0, Long.class);

    List<DepartmentResDto> content = dsl.withRecursive(TB_CTE)
        .select(
            TB_CTE.field("DEPT_CODE").as("deptCode"),
            TB_CTE.field("DEPT_NM").as("deptNm"),
            TB_CTE.field("UPPER_DEPT_CODE").as("upperDeptCode"),
            DSL.field(COMPANY.COMPANY_CODE).as("company.companyCode"),
            DSL.field(COMPANY.COMPANY_NAME).as("company.companyName"),
            TB_CTE.field("DEPT_LV").as("deptLv"),
            TB_CTE.field("USE_YN").as("useYn"))
        .from(TB_CTE)
        .innerJoin(COMPANY)
        .on(
            TB_CTE.field("COMPANY_CODE", String.class).eq(COMPANY.COMPANY_CODE)
                .and(COMPANY.STS.eq("C")))
        .where(condition) // 조건 처리 수정
        .orderBy(sortFields)
        // .limit(pageable.getPageSize())
        // .offset((int) pageable.getOffset())
        .fetchInto(DepartmentResDto.class);

    return new PageImpl<>(content, pageable, total);
  }
}
