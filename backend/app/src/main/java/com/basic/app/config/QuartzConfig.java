package com.basic.app.config;

import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.basic.app.quartz.AutowiringSpringBeanJobFactory;

@Configuration
public class QuartzConfig {

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setJobFactory(springBeanJobFactory()); // Spring 주입 필요 시
    return factory;
  }

  @Bean
  public JobFactory springBeanJobFactory() {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    return jobFactory;
  }
}