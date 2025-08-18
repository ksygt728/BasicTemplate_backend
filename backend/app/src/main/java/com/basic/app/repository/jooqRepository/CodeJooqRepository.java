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
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.jooq.generated.tables.TbComCodeD;
import com.basic.app.jooq.generated.tables.TbComCodeM;
import com.basic.app.jooq.generated.tables.TbComCodeT;
import com.basic.app.util.Status;

@Repository
@Transactional
public class CodeJooqRepository {

  @Autowired
  private DSLContext dsl;

  public Page<CodeSearchFormResDto> findAllCodeMWithConditions(CodeSearchFormReqDto reqDto, Pageable pageable) {
    List<Condition> conditions = new ArrayList<>();

    // 테이블 alias를 CM, CT, CD로 사용
    TbComCodeM CM = TbComCodeM.TB_COM_CODE_M.as("CM");
    TbComCodeT CT = TbComCodeT.TB_COM_CODE_T.as("CT");
    TbComCodeD CD = TbComCodeD.TB_COM_CODE_D.as("CD");

    /**
     * 1. 조건 추가 부분
     * 
     */
    TbComCodeT TB_COM_CODE_T = TbComCodeT.TB_COM_CODE_T;
    TbComCodeD TB_COM_CODE_D = TbComCodeD.TB_COM_CODE_D;

    if (reqDto.getGrpCdType() != null && !reqDto.getGrpCdType().isEmpty()) {
      conditions.add(CM.GRP_CD_TYPE.like("%" + reqDto.getGrpCdType() + "%"));
    }

    if (reqDto.getGrpCd() != null && !reqDto.getGrpCd().isEmpty()) {
      conditions.add(CM.GRP_CD.like("%" + reqDto.getGrpCd() + "%"));
    }

    if (reqDto.getGrpNm() != null && !reqDto.getGrpNm().isEmpty()) {
      conditions.add(CM.GRP_NM.like("%" + reqDto.getGrpNm() + "%"));
    }

    if (reqDto.getAttrCd() != null && !reqDto.getAttrCd().isEmpty()) {
      conditions.add(DSL.exists(
          DSL.selectOne()
              .from(TB_COM_CODE_T)
              .where(TB_COM_CODE_T.GRP_CD.eq(CM.GRP_CD)
                  .and(TB_COM_CODE_T.ATTR_CD.like("%" + reqDto.getAttrCd() + "%"))
                  .and(TB_COM_CODE_T.STS.eq(Status.POSITIVE)))));
    }

    if (reqDto.getAttrNm() != null && !reqDto.getAttrNm().isEmpty()) {
      conditions.add(DSL.exists(
          DSL.selectOne()
              .from(TB_COM_CODE_T)
              .where(TB_COM_CODE_T.GRP_CD.eq(CM.GRP_CD)
                  .and(TB_COM_CODE_T.ATTR_NM.like("%" + reqDto.getAttrNm() + "%"))
                  .and(TB_COM_CODE_T.STS.eq(Status.POSITIVE)))));
    }

    if (reqDto.getDtlCd() != null && !reqDto.getDtlCd().isEmpty()) {
      conditions.add(DSL.exists(
          DSL.selectOne()
              .from(TB_COM_CODE_D)
              .where(TB_COM_CODE_D.GRP_CD.eq(CM.GRP_CD)
                  .and(TB_COM_CODE_D.DTL_CD.like("%" + reqDto.getDtlCd() + "%"))
                  .and(TB_COM_CODE_D.STS.eq(Status.POSITIVE)))));
    }

    if (reqDto.getDtlNm() != null && !reqDto.getDtlNm().isEmpty()) {
      conditions.add(DSL.exists(
          DSL.selectOne()
              .from(TB_COM_CODE_D)
              .where(TB_COM_CODE_D.GRP_CD.eq(CM.GRP_CD)
                  .and(TB_COM_CODE_D.DTL_NM.like("%" + reqDto.getDtlNm() + "%"))
                  .and(TB_COM_CODE_D.STS.eq(Status.POSITIVE)))));
    }

    if (reqDto.getUseYn() != null && !reqDto.getUseYn().isEmpty()) {
      conditions.add(CD.USE_YN.like("%" + reqDto.getUseYn() + "%"));
    }

    conditions.add(CM.STS.eq(Status.POSITIVE));

    /**
     * 
     * 3. 쿼리 결과 및 카운트
     */

    long total = dsl.selectCount()
        .from(CM)
        .leftOuterJoin(CT)
        .on(CM.GRP_CD.eq(CT.GRP_CD)
            .and(CT.STS.eq(Status.POSITIVE).or(CT.STS.isNull())))
        .leftOuterJoin(CD)
        .on(CD.GRP_CD.eq(CT.GRP_CD)
            .and(CD.ATTR_CD.eq(CT.ATTR_CD)))
        .and(CD.STS.eq(Status.POSITIVE).or(CD.STS.isNull()))
        .where(conditions) // 조건 추가
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchOne(0, Long.class);

    List<CodeSearchFormResDto> data = dsl
        .select(
            CM.GRP_CD_TYPE.as("grpCdType"), CM.GRP_CD.as("grpCd"), CM.GRP_NM.as("grpNm"), CT.ATTR_CD.as("attrCd"),
            CT.ATTR_NM.as("attrNm"), CD.DTL_CD.as("dtlCd"), CD.DTL_NM.as("dtlNm"), CD.USE_YN.as("useYn"),
            CD.ORDER_NUM.as("codeDOrderNum"), CT.ORDER_NUM.as("codeTOrderNum"))
        .from(CM)
        .leftOuterJoin(CT)
        .on(CM.GRP_CD.eq(CT.GRP_CD)
            .and(CT.STS.eq(Status.POSITIVE).or(CT.STS.isNull())))
        .leftOuterJoin(CD)
        .on(CD.GRP_CD.eq(CT.GRP_CD)
            .and(CD.ATTR_CD.eq(CT.ATTR_CD)))
        .and(CD.STS.eq(Status.POSITIVE).or(CD.STS.isNull()))
        .where(conditions) // 조건 추가
        .orderBy(CM.GRP_CD.asc(), CD.ORDER_NUM.asc(), CT.ORDER_NUM.asc()) // 정렬 조건 적용
        .limit(pageable.getPageSize()) // 페이지 크기 적용
        .offset(pageable.getOffset()) // 페이지 오프셋 적용
        .fetchInto(CodeSearchFormResDto.class);

    /* JOOQ의 쿼리결과는 List이고 Page객체를 return하는 기능이 없기떄문에 수동으로 Page객체 생성 */
    return new PageImpl<>(data, pageable, total);

  }

}
