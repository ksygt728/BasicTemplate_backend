package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.ScheM;
import com.basic.app.service.interfaces.SchedulerService;

@Service
public class SchedulerServiceImpl implements SchedulerService {

  @Override
  public Map<String, Object> findAllSchedulerForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> findBySchedulerForAdmin(String scheId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBySchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> findBySchedulerHistoryForAdmin(String scheId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBySchedulerHistoryForAdmin'");
  }

  @Override
  public Map<String, Object> insertSchedulerForAdmin(ScheM scheM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> updateSchedulerForAdmin(ScheM scheM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> executeSchedulerForAdmin(String scheId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'executeSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> deleteSchedulerForAdmin(ScheM scheM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteSchedulerForAdmin'");
  }

}
