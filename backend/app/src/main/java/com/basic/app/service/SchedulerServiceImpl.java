package com.basic.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.ScheHResDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.entity.ScheH;
import com.basic.app.entity.ScheM;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.ClientActionException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.exception.customException.SystemErrorException;
import com.basic.app.quartz.DynamicJob;
import com.basic.app.repository.SchedulerHistoryRepository;
import com.basic.app.repository.SchedulerRepository;
import com.basic.app.repository.jooqRepository.SchedulerJooqRepository;
import com.basic.app.service.interfaces.SchedulerService;
import com.basic.app.util.Status;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class SchedulerServiceImpl implements SchedulerService {

  @Autowired
  private Scheduler scheduler; // SchedulerFactoryBean에서 자동주입

  @Autowired
  private SchedulerRepository schedulerRepository;

  @Autowired
  private SchedulerJooqRepository schedulerJooqRepository;

  @Autowired
  private SchedulerHistoryRepository schedulerHistoryRepository;

  @PostConstruct
  public void init() {

    log.info("SchedulerServiceImpl init() : 스케줄러 초기화");

    List<ScheM> jobs = schedulerRepository.findAll().stream()
        .filter(scheM -> scheM.getUseYn().equals("Y") && scheM.getSts().equals(Status.POSITIVE))
        .toList();

    for (ScheM job : jobs) {
      try {
        JobDetail jobDetail = JobBuilder.newJob(DynamicJob.class)
            .withIdentity(job.getScheId(), job.getScheGroup())
            .usingJobData("className", job.getClassName())
            .usingJobData("methodName", job.getMethodName())
            .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(job.getTriggerName(), job.getScheGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExp()))
            .build();

        scheduler.scheduleJob(jobDetail, trigger); // SchedulerFactoryBean이 관리하는 Scheduler에 잡과 트리거가 저장/등록
      } catch (SchedulerException e) {
        log.error("Failed to schedule job: {}", job.getScheId(), e);
      }
    }
  }

  @Override
  public void init(ScheM scheM) {
    try {
      if (scheM.getUseYn().equals("Y") && scheM.getSts().equals(Status.POSITIVE)) { // 스케줄러를 사용하는 경우

        // 중복등록이 불가능함으로 기존 Job을 삭제
        ScheM existingScheM = schedulerRepository.findById(scheM.getScheId())
            .filter(entity -> entity.getSts().equals(Status.POSITIVE))
            .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULER_NOT_FOUND));

        JobKey jobKey = new JobKey(existingScheM.getScheId(), existingScheM.getScheGroup());
        scheduler.deleteJob(jobKey);

        // 신규 등록
        JobDetail jobDetail = JobBuilder.newJob(DynamicJob.class)
            .withIdentity(scheM.getScheId(), scheM.getScheGroup())
            .usingJobData("className", scheM.getClassName())
            .usingJobData("methodName", scheM.getMethodName())
            .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(scheM.getTriggerName(), scheM.getScheGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(scheM.getCronExp()))
            .build();

        scheduler.scheduleJob(jobDetail, trigger); // SchedulerFactoryBean이 관리하는 Scheduler에 잡과 트리거가 저장/등록

      } else { // 사용하지 않거나 상태가 비활성화된 경우 Job 삭제 처리
        JobKey jobKey = new JobKey(scheM.getScheId(), scheM.getScheGroup());
        scheduler.deleteJob(jobKey); // 트리거도 같이 삭제 됨
      }

    } catch (ObjectAlreadyExistsException e2) {
      throw new ClientActionException(ErrorCode.SCHEDULER_IS_EXCUTING, scheM.getScheId());
    } catch (Exception e) {
      throw new SystemErrorException(e, ErrorCode.SCHEDULER_CREATE_FAILED, scheM.getScheId());
    }
  }

  @Override
  public Map<String, Object> findAllSchedulerForAdmin(ScheMReqDto scheMReqDto,
      Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<ScheMResDto> scheMResDtoList = schedulerJooqRepository.findAllScheMWithConditions(scheMReqDto, pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<ScheMResDto> pagedscheMResDtoList = ModelMapperUtils.map(scheMResDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedscheMResDtoList);

    return data;

  }

  @Override
  public Map<String, Object> findBySchedulerForAdmin(String scheId) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    ScheMResDto scheMEntity = schedulerRepository.findById(scheId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(ScheMResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", scheMEntity);
    return data;
  }

  @Override
  public Map<String, Object> findBySchedulerHistoryForAdmin(String scheId, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 모든 인터페이스 조회
    Page<ScheH> pagedScheHList = schedulerHistoryRepository.findAllByScheIdAndSts(scheId,
        Status.POSITIVE,
        pageable);

    // 2. Entity -> DTO 변환
    PageResponse<ScheHResDto> pageResponseList = ModelMapperUtils.map(pagedScheHList, ScheHResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pageResponseList);

    return data;
  }

  @Override
  public Map<String, Object> executeSchedulerForAdmin(String scheId) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 기존 엔티티 조회
    ScheM scheM = schedulerRepository.findById(scheId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    try {
      JobKey jobKey = new JobKey(scheM.getScheId(), scheM.getScheGroup());
      // triggerJob(JobKey) 메서드를 호출하면, 해당 잡을 즉시 실행시킵니다.
      // 등록된 트리거 스케줄과는 별개로 즉시 한번 실행됩니다.
      scheduler.triggerJob(jobKey);
    } catch (Exception e) {
      e.printStackTrace();
      throw new SystemErrorException(ErrorCode.SCHEDULER_EXCUTE_FAILED, scheId);
    }

    data.put("data", "success");

    return data;
  }

  @Override
  public Map<String, Object> insertSchedulerForAdmin(ScheMReqDto scheMDto) {
    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ScheM scheMEntity = scheMDto.toEntity(ScheM.class);

    // 2. ID로 기존 엔티티 조회
    schedulerRepository.findById(scheMEntity.getScheId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    scheMEntity.setUseYn("N"); // 기본값은 사용안함
    ScheM savedScheMEntity = schedulerRepository.save(scheMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedScheMEntity.toDto(ScheMResDto.class));
    return data;

  }

  @Override
  public Map<String, Object> updateSchedulerForAdmin(ScheMReqDto scheMDto) {
    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    ScheM scheMEntity = scheMDto.toEntity(ScheM.class);

    // 2. ID로 기존 엔티티 조회
    schedulerRepository.findById(scheMEntity.getScheId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    ScheM savedScheMEntity = schedulerRepository.save(scheMEntity);

    init(savedScheMEntity); // 스케줄러 활성화 또는 비활성화 처리

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedScheMEntity.toDto(ScheMResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteSchedulerForAdmin(String scheId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    ScheM scheMEntity = schedulerRepository.findById(scheId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    scheMEntity.setUseYn("N");
    scheMEntity.setSts(Status.NAGATIVE);

    init(scheMEntity);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;
  }

}
