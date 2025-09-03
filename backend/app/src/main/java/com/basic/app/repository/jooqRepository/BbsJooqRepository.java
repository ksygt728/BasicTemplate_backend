/**
* @파일명 : BbsJooqRespository.java
* @설명 : JOOQ를 이용한 조회폼 조건별 동적 처리
* @작성자 : 김승연
* @작성일 : 2025.09.01
* @변경이력 :
* 2025.09.01 김승연 최초 생성
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

import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.jooq.generated.tables.TbBbs;
import com.basic.app.jooq.generated.tables.TbCompany;
import com.basic.app.jooq.generated.tables.TbDepartment;
import com.basic.app.jooq.generated.tables.TbUser;

@Repository
@Transactional
public class BbsJooqRepository {

  @Autowired
  private DSLContext dsl;

  public Page<BbsResDto> findAllBbsWithConditions(BbsReqDto reqDto,
      Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbBbs TB_BBS = TbBbs.TB_BBS;
    TbUser TB_USER = TbUser.TB_USER;
    TbDepartment TB_DEPARTMENT = TbDepartment.TB_DEPARTMENT;
    TbCompany TB_COMPANY = TbCompany.TB_COMPANY;

    /**
     * 1. 조건 추가 부분
     *
     */

    if (reqDto.getBbsType() != null && !reqDto.getBbsType().isEmpty()) {
      conditions.add(TB_BBS.BBS_TYPE.like("%" + reqDto.getBbsType() + "%"));
    }
    if (reqDto.getTitle() != null && !reqDto.getTitle().isEmpty()) {
      conditions.add(TB_BBS.TITLE.like("%" + reqDto.getTitle() + "%"));
    }
    if (reqDto.getContent() != null && !reqDto.getContent().isEmpty()) {
      conditions.add(TB_BBS.CONTENT.like("%" + reqDto.getContent() + "%"));
    }
    if (reqDto.getWritor() != null && !reqDto.getWritor().isEmpty()) {
      conditions.add(TB_BBS.WRITOR.like("%" + reqDto.getWritor() + "%"));
    }

    // 필요한 조건 추가
    conditions.add(TB_BBS.STS.eq("C"));

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
        case "bbsId":
          sortFields.add(isAsc ? TB_BBS.BBS_ID.asc() : TB_BBS.BBS_ID.desc());
          break;
        case "bbsType":
          sortFields.add(isAsc ? TB_BBS.BBS_TYPE.asc() : TB_BBS.BBS_TYPE.desc());
          break;
        case "title":
          sortFields.add(isAsc ? TB_BBS.TITLE.asc() : TB_BBS.TITLE.desc());
        case "writeDate":
          sortFields.add(isAsc ? TB_BBS.WRITE_DATE.asc() : TB_BBS.WRITE_DATE.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     *
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_BBS)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<BbsResDto> data = dsl.select(
        // TB_USER, TB_DEPARTMENT, TB_COMPANY)
        TB_BBS.BBS_ID.as("bbsId"),
        TB_BBS.BBS_TYPE.as("bbsType"),
        TB_BBS.TITLE.as("title"),
        TB_BBS.CONTENT.as("content"),
        TB_BBS.WRITE_DATE.as("writeDate"),
        // fix
        TB_USER.USER_ID.as("writor.userId"),
        TB_USER.NAME.as("writor.name"),
        TB_USER.PHONE_NUM.as("writor.phoneNum"),
        TB_USER.EMAIL.as("writor.email"),
        TB_USER.ROLE.as("writor.role"),
        TB_USER.USER_TYPE.as("writor.userType"),
        TB_USER.GENDER.as("writor.gender"),
        TB_DEPARTMENT.DEPT_CODE.as("writor.department.deptCode"),
        TB_DEPARTMENT.DEPT_NM.as("writor.department.deptNm"),
        TB_DEPARTMENT.UPPER_DEPT_CODE.as("writor.department.upperDeptCode"),
        TB_DEPARTMENT.DEPT_LV.as("writor.department.deptLv"),
        TB_DEPARTMENT.USE_YN.as("writor.department.useYn"),
        TB_COMPANY.COMPANY_CODE.as("writor.department.company.companyCode"),
        TB_COMPANY.COMPANY_NAME.as("writor.department.company.companyName"))
        .from(TB_BBS)
        .innerJoin(TB_USER).on(TB_BBS.WRITOR.eq(TB_USER.USER_ID).and(TB_USER.STS.eq("C")))
        .leftOuterJoin(TB_DEPARTMENT).on(TB_USER.DEPT_CODE.eq(TB_DEPARTMENT.DEPT_CODE).and(TB_DEPARTMENT.STS.eq("C")))
        .leftOuterJoin(TB_COMPANY)
        .on(TB_DEPARTMENT.COMPANY_CODE.eq(TB_COMPANY.COMPANY_CODE).and(TB_DEPARTMENT.STS.eq("C")))
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(BbsResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
