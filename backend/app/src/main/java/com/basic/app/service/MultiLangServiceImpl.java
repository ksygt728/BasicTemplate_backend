package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.service.interfaces.MultiLangService;

@Service
public class MultiLangServiceImpl implements MultiLangService {

  @Override
  public Map<String, Object> findAllMulLangForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllMulLangForAdmin'");
  }

  @Override
  public Map<String, Object> findByMulLangForAdmin(String langCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByMulLangForAdmin'");
  }

  @Override
  public Map<String, Object> insertMulLangForAdmin(MulLangReqDto mulLang) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertMulLangForAdmin'");
  }

  @Override
  public Map<String, Object> updateMulLangForAdmin(MulLangReqDto mulLang) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateMulLangForAdmin'");
  }

  @Override
  public Map<String, Object> deleteMulLangForAdmin(String langCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteMulLangForAdmin'");
  }

}
