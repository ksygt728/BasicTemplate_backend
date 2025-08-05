package com.basic.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.service.interfaces.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

  @Override
  public Map<String, Object> findAllRoleForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllRoleForAdmin'");
  }

  @Override
  public Map<String, Object> findByRoleForAdmin(String roldCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByRoleForAdmin'");
  }

  @Override
  public Map<String, Object> insertRoleForAdmin(RoleReqDto role) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertRoleForAdmin'");
  }

  @Override
  public Map<String, Object> updateRoleForAdmin(RoleReqDto role) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateRoleForAdmin'");
  }

  @Override
  public Map<String, Object> deleteRoleForAdmin(String roleCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteRoleForAdmin'");
  }

  @Override
  public Map<String, Object> findByRoleMenuForAdmin(String roldCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByRoleMenuForAdmin'");
  }

  @Override
  public Map<String, Object> updateRoleMenuForAdmin(List<RoleReqDto> roleMenu) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateRoleMenuForAdmin'");
  }

  @Override
  public Map<String, Object> findByRoleUserForAdmin(String uesrId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByRoleUserForAdmin'");
  }

  @Override
  public Map<String, Object> findAllRoleUserForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllRoleUserForAdmin'");
  }

  @Override
  public Map<String, Object> insertRoleUserForAdmin(RoleUserReqDto roleUser) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertRoleUserForAdmin'");
  }

  @Override
  public Map<String, Object> deleteRoleUserForAdmin(List<RoleUserReqDto> roleUser) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteRoleUserForAdmin'");
  }

}
