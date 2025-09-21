package com.basic.app.config;

import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.basic.app.quartz.AutowiringSpringBeanJobFactory;

/**
 * @파일명 : QuartzConfig.java
 * @설명 : Quartz 스케줄러 설정 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 */
@Configuration
public class QuartzConfig {

  /**
   * @기능 : Quartz 스케줄러 팩토리 빈 설정
   * @return SchedulerFactoryBean 스케줄러 팩토리 빈 객체
   */
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setJobFactory(springBeanJobFactory()); // Spring 주입 필요 시
    return factory;
  }

  /**
   * @기능 : Spring Bean 주입이 가능한 Job Factory 생성
   * @return JobFactory Spring Bean 주입이 가능한 Job Factory 객체
   */
  @Bean
  public JobFactory springBeanJobFactory() {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    return jobFactory;
  }
}