package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.SmsM;
import com.basic.app.service.interfaces.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

  @Override
  public Map<String, Object> findAllSmsForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllSmsForAdmin'");
  }

  @Override
  public Map<String, Object> findBySmsForAdmin(String smsId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBySmsForAdmin'");
  }

  @Override
  public Map<String, Object> findBySmsHistoryForAdmin(String smsId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBySmsHistoryForAdmin'");
  }

  @Override
  public Map<String, Object> insertSmsForAdmin(SmsM smsM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertSmsForAdmin'");
  }

  @Override
  public Map<String, Object> updateSmsForAdmin(SmsM smsM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSmsForAdmin'");
  }

  @Override
  public Map<String, Object> deleteSmsForAdmin(String smsId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteSmsForAdmin'");
  }

}