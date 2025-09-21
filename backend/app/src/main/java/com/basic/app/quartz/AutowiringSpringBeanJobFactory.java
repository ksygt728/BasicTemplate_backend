package com.basic.app.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * @파일명 : AutowiringSpringBeanJobFactory.java
 * @설명 : Spring Bean 자동 주입을 위한 Job Factory 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Component
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    /**
     * @기능 : Job 인스턴스 생성 및 Spring Bean 주입
     * @param bundle TriggerFiredBundle
     * @return Job 인스턴스
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // Job 인스턴스 생성
        Object jobInstance = super.createJobInstance(bundle);
        // Spring Bean 주입 처리
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}