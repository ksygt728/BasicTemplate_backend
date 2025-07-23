package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.Interface;

public interface InterfaceService {

  Map<String, Object> findAllInterfaceForAdmin();

  Map<String, Object> findByInterfaceForAdmin(String ifId);

  Map<String, Object> executeInterfaceForAdmin(Interface ifc);

  Map<String, Object> findByInterfaceHistoryForAdmin(String ifId);

  Map<String, Object> insertInterfaceForAdmin(Interface ifc);

  Map<String, Object> updateInterfaceForAdmin(Interface ifc);

  Map<String, Object> deleteInterfaceForAdmin(String ifId);

}
