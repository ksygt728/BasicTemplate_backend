package com.basic.app.util;

import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDetail<T> {
  private String url;
  private String testName;
  private T testData;
  private ApiResponse<?> expected;
  private ResultMatcher httpStatus;
}
