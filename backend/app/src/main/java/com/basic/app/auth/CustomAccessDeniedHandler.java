/**
 * @파일명   : CustomAuthenticationEntryPoint.java
 * @설명     : 인가(403) 처리
 *  인증 & 인가의 경우, 
 *  SeurityFilterChain에서 바로 예외를 던지기 때문에
 *  전역에러핸들러에서 잡을 수 없으므로 별도 핸들러로 처리
 *          
 * @작성자   : 김승연
 * @작성일   : 2025.07.31
 * @변경이력 :
 *   2025.07.31     김승연       최초 생성
 */
package com.basic.app.auth;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.basic.app.api.ApiResponse;
import com.basic.app.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

    log.error(
        """

              [*** Response Error Message ***]
              - ErrorCode : {}
              - Message : {}
              [*** Server Log ***]
              - Class : {}
              - Message : {}
              [*** Strace ***]
              {}
            """,
        errorCode.getCode(),
        errorCode.getMessage(),
        accessDeniedException.getClass(),
        accessDeniedException.getMessage(),
        accessDeniedException.getStackTrace());

    // 응답 객체 구성
    ApiResponse<Object> apiResponse = ApiResponse.fail(ErrorCode.ACCESS_DENIED);

    // JSON 변환 후 응답
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    objectMapper.writeValue(response.getWriter(), apiResponse);
  }
}