package com.basic.app.util;

import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestCaseDetail<T> {
  private String url;
  private String testName;
  private T testData;
  private ApiResponse<?> expected;
  private ResultMatcher httpStatus;
}
