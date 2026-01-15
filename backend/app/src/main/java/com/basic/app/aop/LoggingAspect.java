package com.basic.app.aop;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.basic.app.annotation.NoAopLogging;
import com.basic.app.annotation.NoKafkaLogging;
import com.basic.app.api.ResponseApi;
import com.basic.app.entity.LogApi;
import com.basic.app.util.TimeKeeper;
import com.basic.app.util.UserRequestInfoManager;
import com.basic.app.util.XConverter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @파일명 : LoggingAspect.java
 * @설명 : 컨트롤러 메서드 실행 로깅을 위한 AOP 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.24
 * @변경이력 :
 *       2025.07.24 김승연 최초 생성
 *       2025.12.14 PermissionCheckAspect추가로 order 2로 설정
 *       2025.12.16 @NoKafkaLogging 추가하여 Admin 로그 조회 시 Kafka 전송 제외
 *       처리 @NoAopLogging 추가하여 AOP 로깅 제외 처리
 */
@Aspect
@Component
@Slf4j
@Order(2)
@Profile({ "local", "dev", "qa", "prod" })
public class LoggingAspect {

  @Autowired
  private TimeKeeper timeKeeper;

  @Autowired
  private XConverter xConverter;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  /**
   * @기능 : com.basic.app.controller 이하의 모든 메서드를 대상으로 하는 Pointcut 정의
   */
  @Pointcut("execution(* com.basic.app.controller..*(..))")
  public void controllerMethods() {
  }

  /**
   * @기능 : 컨트롤러 메서드 실행 시 로깅 처리
   * @param joinPoint 실행될 메서드의 정보
   * @return 메서드 실행 결과
   * @throws Throwable 메서드 실행 중 발생할 수 있는 예외
   */
  @Around("controllerMethods()")
  public Object logExecutionInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    LocalDateTime startTime = LocalDateTime.now();

    // HttpServletRequest 가져오기
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest();

    // 메서드 정보
    String className = joinPoint.getSignature().getDeclaringTypeName();
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    String methodName = joinPoint.getSignature().getName();
    String fullMethodName = className + "." + methodName + "()";

    if (method.isAnnotationPresent(NoAopLogging.class)
        || method.getDeclaringClass().isAnnotationPresent(NoAopLogging.class)) {
      return joinPoint.proceed();
    }

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
    if (!(method.isAnnotationPresent(NoKafkaLogging.class)
        || method.getDeclaringClass().isAnnotationPresent(NoKafkaLogging.class))) {

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