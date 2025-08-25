/**
 * @파일명   : UserJooqRepository.java
 * @설명     : JOOQ를 이용한 조회폼 조건별 동적 처리
 * @작성자   : 김승연
 * @작성일   : 2025.07.25
 * @변경이력 :
 *   2025.07.25     김승연       최초 생성
 */

package com.basic.app.repository.jooqRepository;

import java.time.LocalDateTime;
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

import com.basic.app.dto.requestDto.LogApiReqDto;
import com.basic.app.dto.responseDto.LogApiResDto;
import com.basic.app.jooq.generated.tables.TbLogApi;
import com.basic.app.util.TimeKeeper;

@Repository
@Transactional
public class LogApiJooqRepository {

  @Autowired
  private DSLContext dsl;

  @Autowired
  private TimeKeeper timeKeeper;

  public Page<LogApiResDto> findAllLogApiWithConditions(LogApiReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbLogApi TB_LOG_API = TbLogApi.TB_LOG_API;

    /**
     * 1. 조건 추가 부분
     * 
     */
    if (reqDto.getLogId() != null && !reqDto.getLogId().isEmpty()) {
      conditions.add(TB_LOG_API.LOG_ID.like("%" + reqDto.getLogId() + "%"));
    }

    if (reqDto.getUserId() != null && !reqDto.getUserId().isEmpty()) {
      conditions.add(TB_LOG_API.USER_ID.like("%" + reqDto.getUserId() + "%"));
    }

    // "2025-08-07~2025-08-08" 형식의 기간 조건 처리
    if (reqDto.getStartDate() != null && reqDto.getStartDate().contains("~")) {
      LocalDateTime from = timeKeeper.convertStringToLocalDateTimeAtStart(reqDto.getStartDate());
      LocalDateTime to = timeKeeper.convertStringToLocalDateTimeAtEnd(reqDto.getStartDate());
      conditions.add(TB_LOG_API.START_DATE.between(from, to));
    }

    if (reqDto.getEndDate() != null && reqDto.getEndDate().contains("~")) {
      LocalDateTime from = timeKeeper.convertStringToLocalDateTimeAtStart(reqDto.getEndDate());
      LocalDateTime to = timeKeeper.convertStringToLocalDateTimeAtEnd(reqDto.getEndDate());
      conditions.add(TB_LOG_API.START_DATE.between(from, to));
    }

    if (reqDto.getIpAddr() != null && !reqDto.getIpAddr().isEmpty()) {
      conditions.add(TB_LOG_API.IP_ADDR.like("%" + reqDto.getIpAddr() + "%"));
    }

    if (reqDto.getRequestUri() != null && !reqDto.getRequestUri().isEmpty()) {
      conditions.add(TB_LOG_API.REQUEST_URI.like("%" + reqDto.getRequestUri() + "%"));
    }

    if (reqDto.getHttpMethod() != null && !reqDto.getHttpMethod().isEmpty()) {
      conditions.add(TB_LOG_API.HTTP_METHOD.like("%" + reqDto.getHttpMethod() + "%"));
    }

    if (reqDto.getRequestBody() != null && !reqDto.getRequestBody().isEmpty()) {
      conditions.add(TB_LOG_API.REQUEST_BODY.like("%" + reqDto.getRequestBody() + "%"));
    }

    if (reqDto.getResponseBody() != null && !reqDto.getResponseBody().isEmpty()) {
      conditions.add(TB_LOG_API.RESPONSE_BODY.like("%" + reqDto.getResponseBody() + "%"));
    }

    if (reqDto.getStatusCode() != null && !reqDto.getStatusCode().isEmpty()) {
      conditions.add(TB_LOG_API.STATUS_CODE.like("%" + reqDto.getStatusCode() + "%"));
    }

    // 필요한 조건 추가

    conditions.add(TB_LOG_API.STS.eq("C"));

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
          sortFields.add(isAsc ? TB_LOG_API.USER_ID.asc() : TB_LOG_API.USER_ID.desc());
          break;
        case "createDate":
          sortFields.add(isAsc ? TB_LOG_API.CREATE_DATE.asc() : TB_LOG_API.CREATE_DATE.desc());
          break;
        case "startDate":
          sortFields.add(isAsc ? TB_LOG_API.START_DATE.asc() : TB_LOG_API.START_DATE.desc());
          break;
        case "endDate":
          sortFields.add(isAsc ? TB_LOG_API.END_DATE.asc() : TB_LOG_API.END_DATE.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_LOG_API)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<LogApiResDto> data = dsl.selectFrom(TB_LOG_API)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(LogApiResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
