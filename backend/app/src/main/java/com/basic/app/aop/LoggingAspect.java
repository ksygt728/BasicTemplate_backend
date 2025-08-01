package com.basic.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  private final ObjectMapper objectMapper = new ObjectMapper();

  // Pointcut: com.basic.app.controller 이하의 모든 메서드
  @Pointcut("execution(* com.basic.app.controller..*(..))")
  public void controllerMethods() {
  }

  @Around("controllerMethods()")
  public Object logExecutionInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();

    // HttpServletRequest 가져오기
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest();

    // 메서드 정보
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    String fullMethodName = className + "." + methodName + "()";

    // 요청 파라미터 JSON 변환
    String requestJson = serializeArgsToJson(joinPoint.getArgs());

    log.info("""

        🔔 [REQUEST(AOP)] {} {}
          - Method: {}
          - Params: {}

          """,
        request.getMethod(), request.getRequestURI(), fullMethodName, requestJson);

    // 실제 메서드 실행
    Object result;
    try {
      result = joinPoint.proceed();
    } catch (Throwable ex) {
      log.error("❌ [EXCEPTION(AOP)] {} - {}", fullMethodName, ex.getMessage());
      throw ex;
    }

    // 응답 JSON 변환
    String responseJson = serializeObjectToJson(result);

    long endTime = System.currentTimeMillis();

    log.info("""

        ✅ [RESPONSE(AOP)] {}
        - Result: {}
        - 실행시간: {}ms
        """,
        fullMethodName, responseJson, (endTime - startTime));

    return result;
  }

  private String serializeArgsToJson(Object[] args) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(args);
    } catch (Exception e) {
      return "[Unserializable request params]";
    }
  }

  private String serializeObjectToJson(Object obj) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      return "[Unserializable response]";
    }
  }
}