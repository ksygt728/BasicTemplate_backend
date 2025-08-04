package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.service.interfaces.MenuService;

@Transactional
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
  public Map<String, Object> insertMenuForAdmin(MenuReqDto menu) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertMenuForAdmin'");
  }

  @Override
  public Map<String, Object> updateMenuForAdmin(MenuReqDto menu) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateMenuForAdmin'");
  }

  @Override
  public Map<String, Object> deleteMenuForAdmin(String menuCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteMenuForAdmin'");
  }

}
