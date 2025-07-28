package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.service.interfaces.BbsService;

@Service
public class BbsServiceImpl implements BbsService {

  @Override
  public Map<String, Object> findAllBbsForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllBbsForAdmin'");
  }

  @Override
  public Map<String, Object> findByBbsForAdmin(String bbsId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByBbsForAdmin'");
  }

  @Override
  public Map<String, Object> insertBbsForAdmin(BbsReqDto bbs) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertBbsForAdmin'");
  }

  @Override
  public Map<String, Object> updateBbsForAdmin(BbsReqDto bbs) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateBbsForAdmin'");
  }

  @Override
  public Map<String, Object> deleteBbsForAdmin(String bbsId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteBbsForAdmin'");
  }

}
