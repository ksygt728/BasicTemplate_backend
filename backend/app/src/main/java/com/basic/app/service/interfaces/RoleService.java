package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;

public interface RoleService {

  Map<String, Object> findAllRoleForAdmin();

  Map<String, Object> findByRoleForAdmin(String roldCd);

  Map<String, Object> insertRoleForAdmin(RoleReqDto role);

  Map<String, Object> updateRoleForAdmin(RoleReqDto role);

  Map<String, Object> deleteRoleForAdmin(String roleCd);

  Map<String, Object> findByRoleMenuForAdmin(String roldCd);

  Map<String, Object> updateRoleMenuForAdmin(List<RoleReqDto> roleMenu);

  Map<String, Object> findByRoleUserForAdmin(String uesrId);

  Map<String, Object> findAllRoleUserForAdmin();

  Map<String, Object> insertRoleUserForAdmin(RoleUserReqDto roleUser);

  Map<String, Object> deleteRoleUserForAdmin(List<RoleUserReqDto> roleUser);

}
