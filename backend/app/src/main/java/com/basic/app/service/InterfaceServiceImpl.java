package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.Interface;
import com.basic.app.service.interfaces.InterfaceService;

@Service
public class InterfaceServiceImpl implements InterfaceService {

  @Override
  public Map<String, Object> findAllInterfaceForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllInterfaceForAdmin'");
  }

  @Override
  public Map<String, Object> findByInterfaceForAdmin(String ifId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByInterfaceForAdmin'");
  }

  @Override
  public Map<String, Object> executeInterfaceForAdmin(Interface ifc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'executeInterfaceForAdmin'");
  }

  @Override
  public Map<String, Object> findByInterfaceHistoryForAdmin(String ifId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByInterfaceHistoryForAdmin'");
  }

  @Override
  public Map<String, Object> insertInterfaceForAdmin(Interface ifc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertInterfaceForAdmin'");
  }

  @Override
  public Map<String, Object> updateInterfaceForAdmin(Interface ifc) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateInterfaceForAdmin'");
  }

  @Override
  public Map<String, Object> deleteInterfaceForAdmin(String ifId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteInterfaceForAdmin'");
  }

}
