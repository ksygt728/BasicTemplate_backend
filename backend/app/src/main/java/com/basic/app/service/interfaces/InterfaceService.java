package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.InterfaceReqDto;

public interface InterfaceService {

  Map<String, Object> findAllInterfaceForAdmin();

  Map<String, Object> findByInterfaceForAdmin(String ifId);

  Map<String, Object> executeInterfaceForAdmin(InterfaceReqDto ifc);

  Map<String, Object> findByInterfaceHistoryForAdmin(String ifId);

  Map<String, Object> insertInterfaceForAdmin(InterfaceReqDto ifc);

  Map<String, Object> updateInterfaceForAdmin(InterfaceReqDto ifc);

  Map<String, Object> deleteInterfaceForAdmin(String ifId);

}
