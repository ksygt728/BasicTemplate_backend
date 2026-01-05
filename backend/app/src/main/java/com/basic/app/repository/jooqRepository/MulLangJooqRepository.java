
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

import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.entity.jooq.tables.TbMulLang;

/**
 * @파일명 : MulLangJooqRepository.java
 * @설명 : JOOQ를 이용한 다국어 조회폼 조건별 동적 처리
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Repository
@Transactional
public class MulLangJooqRepository {

  @Autowired
  private DSLContext dsl;

  /**
   * @기능 : 다국어 목록 조회 (조건별 동적 쿼리, 페이징)
   * @param reqDto   다국어 검색 조건 DTO
   * @param pageable 페이징 정보
   * @return 다국어 목록 페이지
   */
  public Page<MulLangResDto> findAllMulLangWithConditions(MulLangReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbMulLang TB_MUL_LANG = TbMulLang.TB_MUL_LANG;

    /**
     * 1. 조건 추가 부분
     *
     */
    if (reqDto.getLangCd() != null && !reqDto.getLangCd().isEmpty()) {
      conditions.add(TB_MUL_LANG.LANG_CD.like("%" + reqDto.getLangCd() + "%"));
    }
    if (reqDto.getLangGubun() != null && !reqDto.getLangGubun().isEmpty()) {
      conditions.add(TB_MUL_LANG.LANG_GUBUN.like("%" + reqDto.getLangGubun() + "%"));
    }
    if (reqDto.getLangType() != null && !reqDto.getLangType().isEmpty()) {
      conditions.add(TB_MUL_LANG.LANG_TYPE.like("%" + reqDto.getLangType() + "%"));
    }
    if (reqDto.getLangNm() != null && !reqDto.getLangNm().isEmpty()) {
      conditions.add(TB_MUL_LANG.LANG_NM.like("%" + reqDto.getLangNm() + "%"));
    }
    if (reqDto.getUseYn() != null && !reqDto.getUseYn().isEmpty()) {
      conditions.add(TB_MUL_LANG.USE_YN.like("%" + reqDto.getUseYn() + "%"));
    }

    // 필요한 조건 추가
    conditions.add(TB_MUL_LANG.STS.eq("C"));

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
        case "langCd":
          sortFields.add(isAsc ? TB_MUL_LANG.LANG_CD.asc() : TB_MUL_LANG.LANG_CD.desc());
          break;
        case "langType":
          sortFields.add(isAsc ? TB_MUL_LANG.LANG_TYPE.asc() : TB_MUL_LANG.LANG_TYPE.desc());
          break;
        case "langNm":
          sortFields.add(isAsc ? TB_MUL_LANG.LANG_NM.asc() : TB_MUL_LANG.LANG_NM.desc());
          break;
        case "langGubun":
          sortFields.add(isAsc ? TB_MUL_LANG.LANG_GUBUN.asc() : TB_MUL_LANG.LANG_GUBUN.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     *
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(TB_MUL_LANG)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<MulLangResDto> data = dsl.selectFrom(TB_MUL_LANG)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(MulLangResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
