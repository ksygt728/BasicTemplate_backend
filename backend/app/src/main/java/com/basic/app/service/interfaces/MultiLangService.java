package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.MulLangReqDto;

public interface MultiLangService {

  Map<String, Object> findAllMulLangForAdmin(MulLangReqDto mulLangReqDto, Pageable pageable);

  Map<String, Object> findByMulLangForAdmin(String langGubun, String langCd);

  Map<String, Object> insertMulLangForAdmin(MulLangReqDto mulLang);

  Map<String, Object> updateMulLangForAdmin(MulLangReqDto mulLang);

  Map<String, Object> deleteMulLangForAdmin(String langGubun, String langCd);

  Map<String, Object> deleteMulLangDetailForAdmin(String langType, String langGubun, String langCd);
}
