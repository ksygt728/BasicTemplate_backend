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

import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.dto.responseDto.InterfaceResDto;
import com.basic.app.jooq.generated.tables.TbIf;

@Repository
@Transactional
public class InterfaceJooqRepository {

  @Autowired
  private DSLContext dsl;

  public Page<InterfaceResDto> findAllInterfaceWithConditions(InterfaceReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    TbIf INTERFACE = TbIf.TB_IF;

    /**
     * 1. 조건 추가 부분
     * 
     */
    if (reqDto.getIfId() != null && !reqDto.getIfId().isEmpty()) {
      conditions.add(INTERFACE.IF_ID.like("%" + reqDto.getIfId() + "%"));
    }

    if (reqDto.getIfName() != null && !reqDto.getIfName().isEmpty()) {
      conditions.add(INTERFACE.IF_NAME.like("%" + reqDto.getIfName() + "%"));
    }
    // 필요한 조건 추가

    conditions.add(INTERFACE.STS.eq("C"));

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
        case "ifId":
          sortFields.add(isAsc ? INTERFACE.IF_ID.asc() : INTERFACE.IF_ID.desc());
          break;
        case "createDate":
          sortFields.add(isAsc ? INTERFACE.CREATE_DATE.asc() : INTERFACE.CREATE_DATE.desc());
          break;
        // 필요한 필드 추가
      }
    }

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(INTERFACE)
        .where(conditions) // 동일한 조건으로 전체 카운트
        .fetchOne(0, Long.class);

    List<InterfaceResDto> data = dsl.selectFrom(INTERFACE)
        .where(conditions) // 조건 추가
        .orderBy(sortFields) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(InterfaceResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }
}
