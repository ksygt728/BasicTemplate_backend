package com.basic.app.quartz;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SchedulerJobListImpl implements SchedulerJobList {
    @Override

    public void test1for5second() {
        log.info("5초마다 실행됩니다");
    }

    @Override
    public void test2for10second() {
        log.info("10초마다 실행됩니다");
    }

    @Override
    public void test3for1minute() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted", e);
        }
        log.info("1분마다 실행됩니다");
    }

}
