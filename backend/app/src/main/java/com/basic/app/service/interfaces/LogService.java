package com.basic.app.service.interfaces;

import java.util.Map;

public interface LogService {

  Map<String, Object> findAllAccessLogForAdmin();

  Map<String, Object> findByAccessLogForAdmin(String logId);

  Map<String, Object> findAllErrorLogForAdmin();

  Map<String, Object> findByErrorLogForAdmin(String errId);

}
