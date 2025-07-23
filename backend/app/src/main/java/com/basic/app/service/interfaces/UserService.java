package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.User;

public interface UserService {

  Map<String, Object> findAllUserForAdmin();

  Map<String, Object> findByUserForAdmin(String userId);

  Map<String, Object> updateUserForAdmin(User user);

  Map<String, Object> deleteUserForAdmin(String userId);

}
