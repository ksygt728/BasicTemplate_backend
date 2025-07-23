package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.MailM;
import com.basic.app.service.interfaces.MailService;

@Service
public class MailServiceImpl implements MailService {

  @Override
  public Map<String, Object> findAllMailForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllMailForAdmin'");
  }

  @Override
  public Map<String, Object> findByMailForAdmin(String mailId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByMailForAdmin'");
  }

  @Override
  public Map<String, Object> findByMailHistoryForAdmin(String mailId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByMailHistoryForAdmin'");
  }

  @Override
  public Map<String, Object> insertMailForAdmin(MailM mailM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertMailForAdmin'");
  }

  @Override
  public Map<String, Object> updateMailForAdmin(MailM mailM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateMailForAdmin'");
  }

  @Override
  public Map<String, Object> deleteMailForAdmin(String mailId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteMailForAdmin'");
  }

}
