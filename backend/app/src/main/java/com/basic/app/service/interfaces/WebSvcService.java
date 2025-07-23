package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.WebSvc;

public interface WebSvcService {

  Map<String, Object> findAllWebserviceForAdmin();

  Map<String, Object> findByWebserviceForAdmin(String svcId);

  Map<String, Object> inesrtWebserviceForAdmin(WebSvc webSvc);

  Map<String, Object> updateWebserviceForAdmin(WebSvc webSvc);

  Map<String, Object> deleteWebserviceForAdmin(String svcId);

  Map<String, Object> executeWebserviceForAdmin(String svcId);

  Map<String, Object> findByWebserviceHistoryForAdmin(String svcId);

}
