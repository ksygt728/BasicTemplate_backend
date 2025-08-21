package com.basic.app.quartz;

public interface SchedulerJobList {

  public void test1for5second();

  public void test2for10second();

  public void test3for1minute();

  public void sendMailToBatch() throws Exception;
}
