package com.basic.app.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ResponseApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCaseDetailSearchForm<T, V> extends TestCaseDetail<T> {

  private PageRequest pageRequest; // 페이지 요청 정보

  public TestCaseDetailSearchForm(String url, String testName, T testData, ResponseApi<?> expected,
      ResultMatcher httpStatus, PageRequest pageRequest) {
    super(url, testName, testData, expected, httpStatus);
    this.pageRequest = pageRequest; // 기본 페이지 요청 정보 설정
  }
}
