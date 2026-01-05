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

import com.basic.app.dto.requestDto.SmsMReqDto;
import com.basic.app.dto.responseDto.SmsMResDto;
import com.basic.app.entity.jooq.tables.TbSmsM;

/**
 * @파일명 : SmsMJooqRepository.java
 * @설명 : SMS 템플릿 조회폼 조건별 동적 처리
 * @작성자 : 김승연
 * @작성일 : 2025.08.23
 * @변경이력 :
 *       2025.08.23 김승연 최초 생성
 */
@Repository
@Transactional
public class SmsMJooqRepository {

  @Autowired
  private DSLContext dsl;

  /**
   * @기능 : SMS 템플릿 목록을 조건별로 동적 조회하여 페이징 처리된 결과 반환
   * @param reqDto   : SMS 템플릿 검색 조건 DTO
   * @param pageable : 페이징 정보
   * @return : 조건에 맞는 SMS 템플릿 목록과 페이징 정보
   */
  public Page<SmsMResDto> findAllSmsMWithConditions(SmsMReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbSmsM TB_SMS_M = TbSmsM.TB_SMS_M;

    /**
     * 1. 조건 추가 부분
     * 
     */
    if (reqDto.getSmsId() != null && !reqDto.getSmsId().isEmpty()) {
      conditions.add(TB_SMS_M.SMS_ID.like("%" + reqDto.getSmsId() + "%"));
    }
    if (reqDto.getLangType() != null && !reqDto.getLangType().isEmpty()) {
      conditions.add(TB_SMS_M.LANG_TYPE.like("%" + reqDto.getLangType() + "%"));
    }
    if (reqDto.getSmsName() != null && !reqDto.getSmsName().isEmpty()) {
      conditions.add(TB_SMS_M.SMS_NAME.like("%" + reqDto.getSmsName() + "%"));
    }
    if (reqDto.getText() != null && !reqDto.getText().isEmpty()) {
      conditions.add(TB_SMS_M.TEXT.like("%" + reqDto.getText() + "%"));
    }
    if (reqDto.getDescription() != null && !reqDto.getDescription().isEmpty()) {
      conditions.add(TB_SMS_M.DESCRIPTION.like("%" + reqDto.getDescription() + "%"));
    }

    // 필요한 조건 추가

    conditions.add(TB_SMS_M.STS.eq("C"));

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
        case "smsId":
          sortFields.add(isAsc ? TB_SMS_M.SMS_ID.asc() : TB_SMS_M.SMS_ID.desc());
          break;
        case "smsName":
          sortFields.add(isAsc ? TB_SMS_M.SMS_NAME.asc() : TB_SMS_M.SMS_NAME.desc());
          break;
        case "description":
          sortFields.add(isAsc ? TB_SMS_M.DESCRIPTION.asc() : TB_SMS_M.DESCRIPTION.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_SMS_M)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<SmsMResDto> data = dsl.selectFrom(TB_SMS_M)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(SmsMResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
