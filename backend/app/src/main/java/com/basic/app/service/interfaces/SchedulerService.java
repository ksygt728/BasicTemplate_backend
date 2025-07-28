package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.ScheMReqDto;

public interface SchedulerService {

  Map<String, Object> findAllSchedulerForAdmin();

  Map<String, Object> findBySchedulerForAdmin(String scheId);

  Map<String, Object> findBySchedulerHistoryForAdmin(String scheId);

  Map<String, Object> insertSchedulerForAdmin(ScheMReqDto scheM);

  Map<String, Object> updateSchedulerForAdmin(ScheMReqDto scheM);

  Map<String, Object> executeSchedulerForAdmin(String scheId);

  Map<String, Object> deleteSchedulerForAdmin(ScheMReqDto scheM);

}
