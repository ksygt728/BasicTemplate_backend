package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.dto.requestDto.ScheMReqDto;
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
  public Map<String, Object> insertSchedulerForAdmin(ScheMReqDto scheM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> updateSchedulerForAdmin(ScheMReqDto scheM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> executeSchedulerForAdmin(String scheId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'executeSchedulerForAdmin'");
  }

  @Override
  public Map<String, Object> deleteSchedulerForAdmin(ScheMReqDto scheM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteSchedulerForAdmin'");
  }

}
