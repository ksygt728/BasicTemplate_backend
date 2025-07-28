package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.dto.requestDto.DepartmentReqDto;
import com.basic.app.service.interfaces.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  @Override
  public Map<String, Object> findAllDepartmentForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllDepartmentForAdmin'");
  }

  @Override
  public Map<String, Object> findByDepartmentForAdmin(String deptCode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByDepartmentForAdmin'");
  }

  @Override
  public Map<String, Object> insertDepartmentForAdmin(DepartmentReqDto department) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertDepartmentForAdmin'");
  }

  @Override
  public Map<String, Object> updateDepartmentForAdmin(DepartmentReqDto department) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateDepartmentForAdmin'");
  }

  @Override
  public Map<String, Object> deleteDepartmentForAdmin(String deptCode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteDepartmentForAdmin'");
  }

}