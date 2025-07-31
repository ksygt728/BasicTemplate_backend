package com.basic.app.util;

import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCaseDetailForSearch<T, V> extends TestCaseDetail<T> {

  private V searchForm; // 조회 폼
  private boolean preSave; // 사전에 먼저 저장이 필요한 테스트 케이스

  public TestCaseDetailForSearch(String url, String testName, T testData, ApiResponse<?> expected,
      ResultMatcher httpStatus, V searchForm, boolean preSave) {
    super(url, testName, testData, expected, httpStatus);
    this.searchForm = searchForm;
    this.preSave = preSave;
  }
}
