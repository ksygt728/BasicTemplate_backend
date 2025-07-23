package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.Menu;
import com.basic.app.service.interfaces.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

  @Override
  public Map<String, Object> findAllMenuForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllMenuForAdmin'");
  }

  @Override
  public Map<String, Object> findByMenuForAdmin(String menuCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByMenuForAdmin'");
  }

  @Override
  public Map<String, Object> insertMenuForAdmin(Menu menu) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertMenuForAdmin'");
  }

  @Override
  public Map<String, Object> updateMenuForAdmin(Menu menu) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateMenuForAdmin'");
  }

  @Override
  public Map<String, Object> deleteMenuForAdmin(String menuCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteMenuForAdmin'");
  }

}
