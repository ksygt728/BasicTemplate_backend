package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.Department;

public interface DepartmentService {

  Map<String, Object> findAllDepartmentForAdmin();

  Map<String, Object> findByDepartmentForAdmin(String deptCode);

  Map<String, Object> insertDepartmentForAdmin(Department department);

  Map<String, Object> updateDepartmentForAdmin(String deptCode);

  Map<String, Object> deleteDepartmentForAdmin(String deptCode);

}
