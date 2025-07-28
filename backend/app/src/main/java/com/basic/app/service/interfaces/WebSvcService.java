package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.WebSvcReqDto;

public interface WebSvcService {

  Map<String, Object> findAllWebserviceForAdmin();

  Map<String, Object> findByWebserviceForAdmin(String svcId);

  Map<String, Object> inesrtWebserviceForAdmin(WebSvcReqDto webSvc);

  Map<String, Object> updateWebserviceForAdmin(WebSvcReqDto webSvc);

  Map<String, Object> deleteWebserviceForAdmin(String svcId);

  Map<String, Object> executeWebserviceForAdmin(String svcId);

  Map<String, Object> findByWebserviceHistoryForAdmin(String svcId);

}
