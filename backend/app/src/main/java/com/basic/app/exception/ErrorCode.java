package com.basic.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // 1000번대: 인증 및 사용자 관련

  LOGIN_REQUIRED("1001", "로그인을 해주세요. (로그인페이지로 이동)"), // 401 CustomAuthenticationEntryPoint에서만 던짐
  UNAUTHORIZED_FAILURE("1002", "인증에 실패했습니다. 로그인정보가 올바르지 않습니다."), // 401 // 로그인시에만 던짐
  ACCESS_DENIED("1003", "접근 권한이 없습니다."), // 403 (JWT문제는 아니지만 권한이 없는경우)

  // 사용 X
  JWT_ISSUE_ACCESS_TOKEN_NOT_EXIST("1004", "Access token이 없습니다. 헤더에 실어주세요."), // 403
  // 사용 X
  JWT_ISSUE_REFRESH_TOKEN_NOT_EXIST("1005", "Refresh token이 없습니다. 헤더에 실어주세요."), // 403

  JWT_ISSUE_ACCESS_TOKEN_EXPIRED("1006",
      "[Client] Access token이 만료되었습니다. RefreshToken으로 갱신해주세요. RefreshToekn만 헤더에 실어서 보내주세요.(만료된 AccessToken을 같이 보내면 안됨)"), // 403
  JWT_ISSUE_REFRESH_TOKEN_EXPIRED("1007", "[Client] Refresh token이 만료되었습니다. 로그인페이지로 이동시켜주세요."), // 403
  JWT_ISSUE_ACCESS_TOKEN_NOT_VERIFIED("1008", "[Client] Access token을 검증할 수 없습니다. 로그인페이지로 이동시켜주세요."), // 403
  JWT_ISSUE_REFRESH_TOKEN_NOT_VERIFIED("1009", "[Client] Refresh token을 검증할 수 없습니다. 로그인페이지로 이동시켜주세요."), // 403
  JWT_ISSUE_ATRT_EXSIT("1010", "[Client] Access Token과 Refresh Token이 모두 있습니다. 잘못된 요청입니다."), // 403

  // 2000번대: 요청 및 페이지 관련
  PAGE_NOT_FOUND("2001", "페이지를 찾을 수 없습니다."),

  // 3000번대: 비즈니스 로직 관련
  VALIDATION_ERROR("3001", "유효성 검사에 실패했습니다."),
  VALIDATION_ERROR_CLIENT("3002", "[@Validated] 클라이언트 유효성 검사에 실패했습니다."),

  // 4000번대: 비즈니스 로직 관련(서비스단에서 던지는거)
  OBJECT_NOT_FOUND("4001", "데이터가 존재하지 않습니다."),
  OBJECT_IS_EXISTED("4002", "이미 존재하는 데이터입니다."),
  DEPARTMENT_NOT_WRITE("4003", "부서 정보는 HR에서 받아오는 정보로 추가/수정/삭제가 불가합니다. HR에 문의하세요."),

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