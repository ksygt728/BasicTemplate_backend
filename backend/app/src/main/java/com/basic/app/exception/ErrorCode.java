package com.basic.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // 1000번대: 인증 및 사용자 관련
  USER_NOT_FOUND("1001", "사용자를 찾을 수 없습니다."),
  PASSWORD_INVALID("1002", "비밀번호가 유효하지 않습니다."),
  UNAUTHORIZED_FAILURE("1003", "인증에 실패했습니다."),
  ACCESS_DENIED("1004", "접근 권한이 없습니다."),

  // 2000번대: 요청 및 페이지 관련
  PAGE_NOT_FOUND("2001", "페이지를 찾을 수 없습니다."),
  INVALID_REQUEST("2002", "잘못된 요청입니다."),

  // 3000번대: 비즈니스 로직 관련
  BUSINESS_LOGIC_ERROR("3001", "비즈니스 로직 오류가 발생했습니다."),
  VALIDATION_ERROR("3002", "유효성 검사에 실패했습니다."),
  VALIDATION_ERROR_CLIENT("3003", "클라이언트 유효성 검사에 실패했습니다."),

  // 4000번대: 비즈니스 로직 관련(서비스단에서 던지는거)
  OBJECT_NOT_FOUND("4001", "DB에서 데이터를 찾을 수 없습니다. 다시 시도해주세요."),
  OBJECT_IS_EXISTED("4002", "이미 존재하는 데이터입니다."),

  // 5000번대: 서버 및 외부 시스템 관련
  SERVER_ERROR("5001", "서버 에러가 발생했습니다. 관리자에게 문의하세요."),
  DATABASE_ERROR("5002", "데이터베이스 오류가 발생했습니다."),
  EXTERNAL_API_ERROR("5003", "외부 API 호출 오류가 발생했습니다."),
  VALIDATION_ERROR_SERVER("5004", "서버 유효성 검사에 실패했습니다."),

  // 9000번대: 기타 및 예상치 못한 오류
  UNEXPECTED_ERROR("9001", "예상치 못한 오류가 발생했습니다."),

  ;

  private final String code;
  private final String message;
}