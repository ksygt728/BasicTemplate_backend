package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.service.interfaces.LogService;

@Service
public class LogServiceImpl implements LogService {

  @Override
  public Map<String, Object> findAllAccessLogForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllAccessLogForAdmin'");
  }

  @Override
  public Map<String, Object> findByAccessLogForAdmin(String logId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByAccessLogForAdmin'");
  }

  @Override
  public Map<String, Object> findAllErrorLogForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllErrorLogForAdmin'");
  }

  @Override
  public Map<String, Object> findByErrorLogForAdmin(String errId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByErrorLogForAdmin'");
  }

}
