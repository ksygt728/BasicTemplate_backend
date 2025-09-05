package com.basic.app.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basic.app.mail.MailSendManager;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : SchedulerJobListImpl.java
 * @설명 : 스케줄러 작업 목록 구현 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Log4j2
@Service
public class SchedulerJobListImpl implements SchedulerJobList {

    @Autowired
    private MailSendManager mailSendManager;

    /**
     * @기능 : 5초마다 실행되는 테스트 작업
     */
    @Override
    public void test1for5second() {
        log.info("5초마다 실행됩니다");
    }

    /**
     * @기능 : 10초마다 실행되는 테스트 작업
     */
    @Override
    public void test2for10second() {
        log.info("10초마다 실행됩니다");
    }

    /**
     * @기능 : 1분마다 실행되는 테스트 작업
     */
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

    /**
     * @기능 : 배치로 메일 전송
     */
    @Override
    public void sendMailToBatch() throws Exception {
        mailSendManager.sendMailToBatch();
    }

}
