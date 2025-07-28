package com.basic.app.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Status {

  public static final String POSITIVE = "C"; // 활성 상태
  public static final String NAGATIVE = "D"; // 비활성 상태

}