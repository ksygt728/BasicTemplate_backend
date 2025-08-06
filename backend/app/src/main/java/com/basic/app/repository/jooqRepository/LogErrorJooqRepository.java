/**
 * @파일명   : UserJooqRepository.java
 * @설명     : JOOQ를 이용한 조회폼 조건별 동적 처리
 * @작성자   : 김승연
 * @작성일   : 2025.07.25
 * @변경이력 :
 *   2025.07.25     김승연       최초 생성
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

import com.basic.app.dto.requestDto.LogErrorReqDto;
import com.basic.app.dto.responseDto.LogErrorResDto;
import com.basic.app.jooq.generated.tables.TbLogError;

@Repository
@Transactional
public class LogErrorJooqRepository {

  @Autowired
  private DSLContext dsl;

  public Page<LogErrorResDto> findAllLogErrorWithConditions(LogErrorReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbLogError TB_LOG_ERROR = TbLogError.TB_LOG_ERROR;

    /**
     * 1. 조건 추가 부분
     * 
     */
    if (reqDto.getErrId() != null && !reqDto.getErrId().isEmpty()) {
      conditions.add(TB_LOG_ERROR.ERR_ID.like("%" + reqDto.getErrId() + "%"));
    }

    if (reqDto.getUserId() != null && !reqDto.getUserId().isEmpty()) {
      conditions.add(TB_LOG_ERROR.USER_ID.like("%" + reqDto.getUserId() + "%"));
    }

    if (reqDto.getIpAddr() != null && !reqDto.getIpAddr().isEmpty()) {
      conditions.add(TB_LOG_ERROR.IP_ADDR.like("%" + reqDto.getIpAddr() + "%"));
    }

    if (reqDto.getRequestUri() != null && !reqDto.getRequestUri().isEmpty()) {
      conditions.add(TB_LOG_ERROR.REQUEST_URI.like("%" + reqDto.getRequestUri() + "%"));
    }

    if (reqDto.getHttpMethod() != null && !reqDto.getHttpMethod().isEmpty()) {
      conditions.add(TB_LOG_ERROR.HTTP_METHOD.like("%" + reqDto.getHttpMethod() + "%"));
    }

    if (reqDto.getErrMsg() != null && !reqDto.getErrMsg().isEmpty()) {
      conditions.add(TB_LOG_ERROR.ERR_MSG.like("%" + reqDto.getErrMsg() + "%"));
    }

    if (reqDto.getErrStack() != null && !reqDto.getErrStack().isEmpty()) {
      conditions.add(TB_LOG_ERROR.ERR_STACK.like("%" + reqDto.getErrStack() + "%"));
    }

    // 필요한 조건 추가

    conditions.add(TB_LOG_ERROR.STS.eq("C"));

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
          sortFields.add(isAsc ? TB_LOG_ERROR.USER_ID.asc() : TB_LOG_ERROR.USER_ID.desc());
          break;
        case "createDate":
          sortFields.add(isAsc ? TB_LOG_ERROR.CREATE_DATE.asc() : TB_LOG_ERROR.CREATE_DATE.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_LOG_ERROR)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchOne(0, Long.class);

    List<LogErrorResDto> data = dsl.selectFrom(TB_LOG_ERROR)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(LogErrorResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
