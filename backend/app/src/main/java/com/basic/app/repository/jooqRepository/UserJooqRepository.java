/**
* @파일명 : UserJooqRepository.java
* @설명 : JOOQ를 이용한 조회폼 조건별 동적 처리
* @작성자 : 김승연
* @작성일 : 2025.08.25
* @변경이력 :
* 2025.08.25 김승연 최초 생성
*/

package com.basic.app.repository.jooqRepository;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.jooq.generated.tables.TbCompany;
import com.basic.app.jooq.generated.tables.TbDepartment;
import com.basic.app.jooq.generated.tables.TbUser;

@Repository
@Transactional
public class UserJooqRepository {

  @Autowired
  private DSLContext dsl;

  public Page<UserResDto> findAllUserWithConditions(UserReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbUser TB_USER = TbUser.TB_USER;
    TbDepartment TB_DEPARTMENT = TbDepartment.TB_DEPARTMENT;
    TbCompany TB_COMPANY = TbCompany.TB_COMPANY;

    /**
     * 1. 조건 추가 부분
     *
     */
    if (reqDto.getUserId() != null && !reqDto.getUserId().isEmpty()) {
      conditions.add(TB_USER.USER_ID.like("%" + reqDto.getUserId() + "%"));
    }
    if (reqDto.getName() != null && !reqDto.getName().isEmpty()) {
      conditions.add(TB_USER.NAME.like("%" + reqDto.getName() + "%"));
    }

    // 필요한 조건 추가
    conditions.add(TB_USER.STS.eq("C"));
    conditions.add(TB_DEPARTMENT.STS.eq("C"));
    conditions.add(TB_COMPANY.STS.eq("C"));

    /**
     * 2. 정렬 처리
     *
     */
    Sort sort = pageable.getSort();

    List<SortField<?>> sortFields = new ArrayList<>();
    for (Sort.Order order : sort) {
      String property = order.getProperty();
      boolean isAsc = order.getDirection().isAscending();

      switch (property) {
        case "userId":
          sortFields.add(isAsc ? TB_USER.USER_ID.asc() : TB_USER.USER_ID.desc());
          break;
        case "name":
          sortFields.add(isAsc ? TB_USER.NAME.asc() : TB_USER.NAME.desc());
          break;

        // 필요한 필드 추가
      }
    }

    /**
     *
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_USER)
        .innerJoin(TB_DEPARTMENT).on(TB_USER.DEPT_CODE.eq(TB_DEPARTMENT.DEPT_CODE))
        .innerJoin(TB_COMPANY).on(TB_DEPARTMENT.COMPANY_CODE.eq(TB_COMPANY.COMPANY_CODE))
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<UserResDto> data = dsl.select(
        // TB_USER, TB_DEPARTMENT, TB_COMPANY)
        TB_USER.USER_ID.as("userId"),
        TB_USER.NAME.as("name"),
        TB_USER.PHONE_NUM.as("phoneNum"),
        TB_USER.EMAIL.as("email"),
        TB_USER.ROLE.as("role"),
        TB_USER.USER_TYPE.as("userType"),
        TB_USER.GENDER.as("gender"),
        TB_DEPARTMENT.DEPT_CODE.as("department.deptCode"),
        TB_DEPARTMENT.DEPT_NM.as("department.deptNm"),
        TB_DEPARTMENT.UPPER_DEPT_CODE.as("department.upperDeptCode"),
        TB_DEPARTMENT.DEPT_LV.as("department.deptLv"),
        TB_DEPARTMENT.USE_YN.as("department.useYn"),
        TB_COMPANY.COMPANY_CODE.as("department.company.companyCode"),
        TB_COMPANY.COMPANY_NAME.as("department.company.companyName"))
        .from(TB_USER)
        .innerJoin(TB_DEPARTMENT).on(TB_USER.DEPT_CODE.eq(TB_DEPARTMENT.DEPT_CODE))
        .innerJoin(TB_COMPANY).on(TB_DEPARTMENT.COMPANY_CODE.eq(TB_COMPANY.COMPANY_CODE))
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(UserResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
