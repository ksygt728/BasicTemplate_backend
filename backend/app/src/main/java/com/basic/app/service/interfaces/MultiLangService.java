package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.MulLang;

public interface MultiLangService {

  Map<String, Object> findAllMulLangForAdmin();

  Map<String, Object> findByMulLangForAdmin(String langCd);

  Map<String, Object> insertMulLangForAdmin(MulLang mulLang);

  Map<String, Object> updateMulLangForAdmin(MulLang mulLang);

  Map<String, Object> deleteMulLangForAdmin(String langCd);

}
