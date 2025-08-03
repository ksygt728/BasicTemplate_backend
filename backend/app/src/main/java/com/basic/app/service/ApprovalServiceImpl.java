package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.service.interfaces.ApprovalService;

@Transactional
@Service
public class ApprovalServiceImpl implements ApprovalService {

  @Override
  public Map<String, Object> findPlannedApprovals() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findPlannedApprovals'");
  }

}
