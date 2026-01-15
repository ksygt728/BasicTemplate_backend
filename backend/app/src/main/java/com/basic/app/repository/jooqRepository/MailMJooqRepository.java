
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

import com.basic.app.dto.requestDto.MailMReqDto;
import com.basic.app.dto.responseDto.MailMResDto;
import com.basic.app.entity.jooq.tables.TbMailM;

/**
 * @파일명 : MailMJooqRepository.java
 * @설명 : JOOQ를 이용한 메일 마스터 조회폼 조건별 동적 처리
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Repository
@Transactional
public class MailMJooqRepository {

  @Autowired
  private DSLContext dsl;

  /**
   * @기능 : 메일 마스터 목록 조회 (조건별 동적 쿼리, 페이징)
   * @param reqDto   메일 검색 조건 DTO
   * @param pageable 페이징 정보
   * @return 메일 마스터 목록 페이지
   */
  public Page<MailMResDto> findAllMailHWithConditions(MailMReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbMailM TB_MAIL_M = TbMailM.TB_MAIL_M;

    /**
     * 1. 조건 추가 부분
     * 
     */
    if (reqDto.getMailId() != null && !reqDto.getMailId().isEmpty()) {
      conditions.add(TB_MAIL_M.MAIL_ID.like("%" + reqDto.getMailId() + "%"));
    }

    if (reqDto.getLangType() != null && !reqDto.getLangType().isEmpty()) {
      conditions.add(TB_MAIL_M.LANG_TYPE.like("%" + reqDto.getLangType() + "%"));
    }

    if (reqDto.getMailName() != null && !reqDto.getMailName().isEmpty()) {
      conditions.add(TB_MAIL_M.MAIL_NAME.like("%" + reqDto.getMailName() + "%"));
    }

    if (reqDto.getTitle() != null && !reqDto.getTitle().isEmpty()) {
      conditions.add(TB_MAIL_M.TITLE.like("%" + reqDto.getTitle() + "%"));
    }

    if (reqDto.getContent() != null && !reqDto.getContent().isEmpty()) {
      conditions.add(TB_MAIL_M.CONTENT.like("%" + reqDto.getContent() + "%"));
    }

    if (reqDto.getDescription() != null && !reqDto.getDescription().isEmpty()) {
      conditions.add(TB_MAIL_M.DESCRIPTION.like("%" + reqDto.getDescription() + "%"));
    }
    // 필요한 조건 추가

    conditions.add(TB_MAIL_M.STS.eq("C"));

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
        case "mailId":
          sortFields.add(isAsc ? TB_MAIL_M.MAIL_ID.asc() : TB_MAIL_M.MAIL_ID.desc());
          break;
        case "mailName":
          sortFields.add(isAsc ? TB_MAIL_M.MAIL_NAME.asc() : TB_MAIL_M.MAIL_NAME.desc());
          break;
        case "title":
          sortFields.add(isAsc ? TB_MAIL_M.TITLE.asc() : TB_MAIL_M.TITLE.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_MAIL_M)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<MailMResDto> data = dsl.selectFrom(TB_MAIL_M)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(MailMResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
