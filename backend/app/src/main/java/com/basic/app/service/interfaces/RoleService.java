package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import com.basic.app.entity.Role;
import com.basic.app.entity.RoleMenu;
import com.basic.app.entity.RoleUser;

public interface RoleService {

  Map<String, Object> findAllRoleForAdmin();

  Map<String, Object> findByRoleForAdmin(String roldCd);

  Map<String, Object> insertRoleForAdmin(Role role);

  Map<String, Object> updateRoleForAdmin(Role role);

  Map<String, Object> deleteRoleForAdmin(String roleCd);

  Map<String, Object> findByRoleMenuForAdmin(String roldCd);

  Map<String, Object> updateRoleMenuForAdmin(List<RoleMenu> roleMenu);

  Map<String, Object> findByRoleUserForAdmin(String uesrId);

  Map<String, Object> findAllRoleUserForAdmin();

  Map<String, Object> insertRoleUserForAdmin(RoleUser roleUser);

  Map<String, Object> deleteRoleUserForAdmin(List<RoleUser> roleUser);

}
