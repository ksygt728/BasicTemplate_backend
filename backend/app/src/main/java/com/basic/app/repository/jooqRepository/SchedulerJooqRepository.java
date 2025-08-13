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

import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.jooq.generated.tables.TbScheM;

@Repository
@Transactional
public class SchedulerJooqRepository {

  @Autowired
  private DSLContext dsl;

  public Page<ScheMResDto> findAllScheMWithConditions(ScheMReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbScheM TB_SCHE_M = TbScheM.TB_SCHE_M;

    /**
     * 1. 조건 추가 부분
     * 
     */
    if (reqDto.getScheId() != null && !reqDto.getScheId().isEmpty()) {
      conditions.add(TB_SCHE_M.SCHE_ID.like("%" + reqDto.getScheId() + "%"));
    }

    if (reqDto.getScheName() != null && !reqDto.getScheName().isEmpty()) {
      conditions.add(TB_SCHE_M.SCHE_NAME.like("%" + reqDto.getScheName() + "%"));
    }

    if (reqDto.getDescription() != null && !reqDto.getDescription().isEmpty()) {
      conditions.add(TB_SCHE_M.DESCRIPTION.like("%" + reqDto.getDescription() + "%"));
    }

    if (reqDto.getScheGroup() != null && !reqDto.getScheGroup().isEmpty()) {
      conditions.add(TB_SCHE_M.SCHE_GROUP.like("%" + reqDto.getScheGroup() + "%"));
    }

    if (reqDto.getClassName() != null && !reqDto.getClassName().isEmpty()) {
      conditions.add(TB_SCHE_M.CLASS_NAME.like("%" + reqDto.getClassName() + "%"));
    }

    if (reqDto.getMethodName() != null && !reqDto.getMethodName().isEmpty()) {
      conditions.add(TB_SCHE_M.METHOD_NAME.like("%" + reqDto.getMethodName() + "%"));
    }

    if (reqDto.getTriggerName() != null && !reqDto.getTriggerName().isEmpty()) {
      conditions.add(TB_SCHE_M.TRIGGER_NAME.like("%" + reqDto.getTriggerName() + "%"));
    }

    if (reqDto.getCronExp() != null && !reqDto.getCronExp().isEmpty()) {
      conditions.add(TB_SCHE_M.CRON_EXP.like("%" + reqDto.getCronExp() + "%"));
    }

    if (reqDto.getUseYn() != null && !reqDto.getUseYn().isEmpty()) {
      conditions.add(TB_SCHE_M.USE_YN.like("%" + reqDto.getUseYn() + "%"));
    }
    // 필요한 조건 추가

    conditions.add(TB_SCHE_M.STS.eq("C"));

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
        case "scheId":
          sortFields.add(isAsc ? TB_SCHE_M.SCHE_ID.asc() : TB_SCHE_M.SCHE_ID.desc());
          break;
        case "scheName":
          sortFields.add(isAsc ? TB_SCHE_M.SCHE_NAME.asc() : TB_SCHE_M.SCHE_NAME.desc());
        case "scheGroup":
          sortFields.add(isAsc ? TB_SCHE_M.SCHE_GROUP.asc() : TB_SCHE_M.SCHE_GROUP.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_SCHE_M)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchOne(0, Long.class);

    List<ScheMResDto> data = dsl.selectFrom(TB_SCHE_M)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(ScheMResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }

}
