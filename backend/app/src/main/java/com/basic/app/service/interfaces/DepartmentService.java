package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.DepartmentReqDto;

public interface DepartmentService {

  Map<String, Object> findAllDepartmentForAdmin();

  Map<String, Object> findByDepartmentForAdmin(String deptCode);

  Map<String, Object> insertDepartmentForAdmin(DepartmentReqDto department);

  Map<String, Object> updateDepartmentForAdmin(DepartmentReqDto department);

  Map<String, Object> deleteDepartmentForAdmin(String deptCode);

}
