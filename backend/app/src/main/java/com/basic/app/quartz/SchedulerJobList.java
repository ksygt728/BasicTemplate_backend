package com.basic.app.quartz;

/**
 * @파일명 : SchedulerJobList.java
 * @설명 : 스케줄러 작업 목록 인터페이스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public interface SchedulerJobList {

  /**
   * @기능 : 5초마다 실행되는 테스트 작업
   */
  public void test1for5second();

  /**
   * @기능 : 10초마다 실행되는 테스트 작업
   */
  public void test2for10second();

  /**
   * @기능 : 1분마다 실행되는 테스트 작업
   */
  public void test3for1minute();

  /**
   * @기능 : 배치로 메일 전송
   */
  public void sendMailToBatch() throws Exception;
}
