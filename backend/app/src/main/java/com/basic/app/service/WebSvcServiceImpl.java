package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.WebSvcReqDto;
import com.basic.app.service.interfaces.WebSvcService;

@Transactional
@Service
public class WebSvcServiceImpl implements WebSvcService {

  @Override
  public Map<String, Object> findAllWebserviceForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllWebserviceForAdmin'");
  }

  @Override
  public Map<String, Object> findByWebserviceForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByWebserviceForAdmin'");
  }

  @Override
  public Map<String, Object> inesrtWebserviceForAdmin(WebSvcReqDto webSvc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'inesrtWebserviceForAdmin'");
  }

  @Override
  public Map<String, Object> updateWebserviceForAdmin(WebSvcReqDto webSvc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateWebserviceForAdmin'");
  }

  @Override
  public Map<String, Object> deleteWebserviceForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteWebserviceForAdmin'");
  }

  @Override
  public Map<String, Object> executeWebserviceForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'executeWebserviceForAdmin'");
  }

  @Override
  public Map<String, Object> findByWebserviceHistoryForAdmin(String svcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByWebserviceHistoryForAdmin'");
  }

}
