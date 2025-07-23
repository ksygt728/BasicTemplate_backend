package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.entity.User;
import com.basic.app.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public Map<String, Object> findAllUserForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllUserForAdmin'");
  }

  @Override
  public Map<String, Object> findByUserForAdmin(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUserForAdmin'");
  }

  @Override
  public Map<String, Object> updateUserForAdmin(User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUserForAdmin'");
  }

  @Override
  public Map<String, Object> deleteUserForAdmin(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteUserForAdmin'");
  }

}
