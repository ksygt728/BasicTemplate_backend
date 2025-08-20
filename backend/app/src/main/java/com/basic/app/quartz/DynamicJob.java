package com.basic.app.quartz;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.entity.ScheH;
import com.basic.app.entity.ScheM;
import com.basic.app.repository.SchedulerHistoryRepository;
import com.basic.app.repository.SchedulerRepository;
import com.basic.app.util.Status;
import com.basic.app.util.TimeKeeper;
import com.basic.app.util.XConverter;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class DynamicJob implements Job {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private XConverter xConverter;

    @Autowired
    private TimeKeeper timeKeeper;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private SchedulerHistoryRepository schedulerHistoryRepository;

    @Transactional
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        /* step 0 : 초기화 */
        LocalDateTime startTime = LocalDateTime.now();

        String className = context.getMergedJobDataMap().getString("className");
        String methodName = context.getMergedJobDataMap().getString("methodName");
        String scheId = context.getJobDetail().getKey().getName();
        String scheGroup = context.getJobDetail().getKey().getGroup();
        Date nextFireDate = context.getTrigger().getNextFireTime(); // <-- 다음 실행 시간
        LocalDateTime nextExecTime = nextFireDate == null ? null
                : timeKeeper.convertDateToLocalDateTime(nextFireDate);
        String success = "N"; // 성공 여부
        String errorMessage = null; // 실패 메시지

        ScheM scheM = schedulerRepository.findById(scheId)
                .filter(sche -> sche.getUseYn().equals("Y") && sche.getSts().equals(Status.POSITIVE))
                .orElse(null);

        /* step 1 : DB에 없는 항목이면 스케줄러에서 Job 삭제 처리(DML로 기준정보만 삭제한 경우) */
        if (scheM == null) {
            log.warn("사용하지 않는 SCHE_ID : {} 이(가) schedulerFactory에 등록되어 실행되고 있습니다. 스케줄러에서 자체적으로 삭제처리합니다.", scheId);
            try {
                scheduler.deleteJob(context.getJobDetail().getKey());
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* step 2 : 스케줄러 실행 */
        try {
            Class<?> clazz = Class.forName(className);

            // Spring Bean을 ApplicationContext에서 가져오기
            Object instance = applicationContext.getBean(clazz);

            Method method = clazz.getMethod(methodName);
            method.invoke(instance);
            success = "Y";

        } catch (Exception e) {
            errorMessage = e.getMessage() + "\n" + getStackTraceAsString(e);
            e.printStackTrace();
        } finally {
            /* step 3 : 스케줄러 업데이트 및 이력 저장 */
            LocalDateTime endTime = LocalDateTime.now();

            if (scheM != null) {
                scheM.setLastExecTime(endTime);
                if (nextExecTime != null) // 직접 실행을 할경우 nextExecTime이 null로들어옴. 제외
                    scheM.setNextExecTime(nextExecTime);
                schedulerRepository.save(scheM);
            }

            ScheH scheH = ScheH.builder()
                    .scheId(scheId)
                    .scheGroup(scheGroup)
                    .startTime(startTime)
                    .endTime(endTime)
                    .execTime(Duration.between(startTime, endTime).toMillis())
                    .success(success)
                    .errorMsg(errorMessage)
                    .build();

            schedulerHistoryRepository.save(scheH);
        }
    }

    private String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}