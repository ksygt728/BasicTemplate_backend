
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

import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.jooq.generated.tables.TbChaebun;

/**
 * @파일명 : ChaebunJooqRepository.java
 * @설명 : JOOQ를 이용한 채번 조회폼 조건별 동적 처리
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Repository
@Transactional
public class ChaebunJooqRepository {

  @Autowired
  private DSLContext dsl;

  /**
   * @기능 : 채번 마스터 목록 조회 (조건별 동적 쿼리, 페이징)
   * @param reqDto   채번 검색 조건 DTO
   * @param pageable 페이징 정보
   * @return 채번 마스터 목록 페이지
   */
  public Page<ChaebunResDto> findAllChaebunMWithConditions(ChaebunReqDto reqDto,
      Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbChaebun TB_CHAEBUN = TbChaebun.TB_CHAEBUN;

    /**
     * 1. 조건 추가 부분
     *
     */
    if (reqDto.getSeqId() != null && !reqDto.getSeqId().isEmpty()) {
      conditions.add(TB_CHAEBUN.SEQ_ID.like("%" + reqDto.getSeqId() + "%"));
    }
    if (reqDto.getSeqName() != null && !reqDto.getSeqName().isEmpty()) {
      conditions.add(TB_CHAEBUN.SEQ_NAME.like("%" + reqDto.getSeqName() + "%"));
    }
    if (reqDto.getPattern() != null && !reqDto.getPattern().isEmpty()) {
      conditions.add(TB_CHAEBUN.PATTERN.like("%" + reqDto.getPattern() + "%"));
    }
    if (reqDto.getPrefix() != null && !reqDto.getPrefix().isEmpty()) {
      conditions.add(TB_CHAEBUN.PREFIX.like("%" + reqDto.getPrefix() + "%"));
    }
    if (reqDto.getStep() != 0) {
      conditions.add(TB_CHAEBUN.STEP.eq(reqDto.getStep()));
    }
    if (reqDto.getLength() != 0) {
      conditions.add(TB_CHAEBUN.LENGTH.eq(reqDto.getLength()));
    }
    if (reqDto.getDateformat() != null && !reqDto.getDateformat().isEmpty()) {
      conditions.add(TB_CHAEBUN.DATEFORMAT.like("%" + reqDto.getDateformat() + "%"));

    }

    // 필요한 조건 추가
    conditions.add(TB_CHAEBUN.STS.eq("C"));

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
        case "seqId":
          sortFields.add(isAsc ? TB_CHAEBUN.SEQ_ID.asc() : TB_CHAEBUN.SEQ_ID.desc());
          break;
        case "seqName":
          sortFields.add(isAsc ? TB_CHAEBUN.SEQ_NAME.asc() : TB_CHAEBUN.SEQ_NAME.desc());
          break;
        case "prefix":
          sortFields.add(isAsc ? TB_CHAEBUN.PREFIX.asc() : TB_CHAEBUN.PREFIX.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     *
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_CHAEBUN)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<ChaebunResDto> data = dsl.selectFrom(TB_CHAEBUN)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(ChaebunResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
