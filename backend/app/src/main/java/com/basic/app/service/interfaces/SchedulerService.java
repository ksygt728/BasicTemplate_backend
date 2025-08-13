package com.basic.app.service.interfaces;

import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.entity.ScheM;

public interface SchedulerService {

  void init();

  void init(ScheM scheM);

  Map<String, Object> findAllSchedulerForAdmin(ScheMReqDto scheMReqDto, Pageable pageable);

  Map<String, Object> findBySchedulerForAdmin(String scheId);

  Map<String, Object> findBySchedulerHistoryForAdmin(String scheId, Pageable pageable);

  Map<String, Object> insertSchedulerForAdmin(ScheMReqDto scheMDto);

  Map<String, Object> updateSchedulerForAdmin(ScheMReqDto scheMDto);

  Map<String, Object> executeSchedulerForAdmin(String scheId);

  Map<String, Object> deleteSchedulerForAdmin(String scheId);

}
