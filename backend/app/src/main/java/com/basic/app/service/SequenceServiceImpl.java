package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.Chaebun;
import com.basic.app.service.interfaces.SequenceService;

@Service
public class SequenceServiceImpl implements SequenceService {

  @Override
  public Map<String, Object> findAllSequenceForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllSequenceForAdmin'");
  }

  @Override
  public Map<String, Object> findBySequenceForAdmin(String seqId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBySequenceForAdmin'");
  }

  @Override
  public Map<String, Object> insertSequenceForAdmin(Chaebun chaebun) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertSequenceForAdmin'");
  }

  @Override
  public Map<String, Object> updateSequenceForAdmin(Chaebun chaebun) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSequenceForAdmin'");
  }

  @Override
  public Map<String, Object> deleteSequenceForAdmin(String seqId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteSequenceForAdmin'");
  }

}
