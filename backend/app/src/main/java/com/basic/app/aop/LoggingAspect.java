package com.basic.app.aop;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.basic.app.api.ResponseApi;
import com.basic.app.entity.LogApi;
import com.basic.app.util.TimeKeeper;
import com.basic.app.util.UserRequestInfoManager;
import com.basic.app.util.XConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Autowired
  private TimeKeeper timeKeeper;

  @Autowired
  private XConverter xConverter;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  // Pointcut: com.basic.app.controller 이하의 모든 메서드
  @Pointcut("execution(* com.basic.app.controller..*(..))")
  public void controllerMethods() {
  }

  @Around("controllerMethods()")
  public Object logExecutionInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    LocalDateTime startTime = LocalDateTime.now();

    // HttpServletRequest 가져오기
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest();

    // 메서드 정보
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    String fullMethodName = className + "." + methodName + "()";

    // 요청 파라미터 JSON 변환
    String requestJson = xConverter.convertArgsToJson(joinPoint.getArgs());

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

    LocalDateTime endTime = LocalDateTime.now();

    // 응답 JSON 변환
    String responseJson = xConverter.convertObjectToJson(result);

    log.info("""

        ✅ [RESPONSE(AOP)] {}
        - Result: {}
        - 실행시간: {}ms
        """,
        fullMethodName, responseJson, Duration.between(startTime, endTime).toMillis());

    // Kafka에 로그 전송(Admin 로그 조회는 Kafka에 전송하지 않음)
    if (!(request.getRequestURI().contains("/admin/log/api-log/search") ||
        request.getRequestURI().contains("/admin/log/error-log/search"))) {

      UserRequestInfoManager userRequestInfoManager = new UserRequestInfoManager(request);

      ResponseEntity<ResponseApi<Map<String, Object>>> responseEntity = (ResponseEntity<ResponseApi<Map<String, Object>>>) result;
      String statusCode = String.valueOf(responseEntity.getStatusCode().value());

      kafkaTemplate.send("log-topic", new LogApi(
          null,
          userRequestInfoManager.getUserId(),
          startTime,
          endTime,
          userRequestInfoManager.getIpAddr(),
          userRequestInfoManager.getUserAgent(),
          userRequestInfoManager.getRequestUri(),
          userRequestInfoManager.getHttpMethod(),
          requestJson,
          responseJson,
          statusCode, // STATUS_CODE
          Duration.between(startTime, endTime).toMillis() // 실행시간
      ));
    }

    return result;
  }

}