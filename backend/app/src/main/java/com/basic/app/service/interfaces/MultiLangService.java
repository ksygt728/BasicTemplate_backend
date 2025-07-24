package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.MulLangReqDto;

public interface MultiLangService {

  Map<String, Object> findAllMulLangForAdmin();

  Map<String, Object> findByMulLangForAdmin(String langCd);

  Map<String, Object> insertMulLangForAdmin(MulLangReqDto mulLang);

  Map<String, Object> updateMulLangForAdmin(MulLangReqDto mulLang);

  Map<String, Object> deleteMulLangForAdmin(String langCd);

}
