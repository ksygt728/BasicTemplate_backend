package com.basic.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND("1001", "사용자를 찾을 수 없습니다."),
  PASSWORD_INVALID("1002", "비밀번호가 유효하지 않습니다."),
  SERVER_ERROR("2000", "서버 에러가 발생했습니다. 관리자에게 문의하세요."),
  UNAUTHORIZED_FAILURE("3000", "인증에 실패했습니다."),
  PAGE_NOT_FOUND("3001", "페이지를 찾을 수 없습니다."),
  ACCESS_DENIED("3002", "접근이 권한이 없습니다."),
  INVALID_REQUEST("4001", "잘못된 요청입니다."),
  BUSINESS_LOGIC_ERROR("4002", "비즈니스 로직 오류가 발생했습니다."),
  VALIDATION_ERROR("4003", "유효성 검사에 실패했습니다."),

  VALIDATION_ERROR_CLIENT("4004", "클라이언트 유효성 검사에 실패했습니다."),
  VALIDATION_ERROR_SERVER("4005", "서버 유효성 검사에 실패했습니다."),

  DATABASE_ERROR("5001", "데이터베이스 오류가 발생했습니다."),
  EXTERNAL_API_ERROR("5002", "외부 API 호출 오류가 발생했습니다."),
  UNEXPECTED_ERROR("9999", "예상치 못한 오류가 발생했습니다."),

  /* 비즈니스 로직에서 발생할 수 있는 에러 코드 */
  // [NotFoundException]
  OBJECT_NOT_FOUND("6001", "DB에서 데이터를 찾을 수 없습니다. 다시 시도해주세요."),
  // [BusinessException]
  ;

  private final String code;
  private final String message;

}