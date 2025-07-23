package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.ScheM;

public interface SchedulerService {

  Map<String, Object> findAllSchedulerForAdmin();

  Map<String, Object> findBySchedulerForAdmin(String scheId);

  Map<String, Object> findBySchedulerHistoryForAdmin(String scheId);

  Map<String, Object> insertSchedulerForAdmin(ScheM scheM);

  Map<String, Object> updateSchedulerForAdmin(ScheM scheM);

  Map<String, Object> executeSchedulerForAdmin(String scheId);

  Map<String, Object> deleteSchedulerForAdmin(ScheM scheM);

}
