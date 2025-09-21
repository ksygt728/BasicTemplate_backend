package com.basic.app.featureTest.testcases.menu;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.basic.app.api.ResponseApi;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : MenuTestCasesForDelete.java
 * @설명 : 메뉴 정보 삭제 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.01.07
 * @변경이력 :
 *       2025.01.07 김승연 최초 생성
 */
public class MenuTestCasesForDelete implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/menu";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<>();

    /*
     * [TC_ID : TC-041] [TC명 : 메뉴 정보 삭제1] [REQ_ID : REQ_ADM_041] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]USR001_003", "정상 삭제", "[JUnit]USR001_003",
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-042] [TC명 : 메뉴 정보 삭제2] [REQ_ID : REQ_ADM_041] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]DEL001", "이미 삭제한 항목 삭제 시도", "[JUnit]DEL001",
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND, "[JUnit]DEL001"),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-043] [TC명 : 메뉴 정보 삭제3] [REQ_ID : REQ_ADM_041] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]NOT_EXIST", "존재하지 않는 ID 삭제 시도", "[JUnit]NOT_EXIST",
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND, "[JUnit]NOT_EXIST"),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-044] [TC명 : 메뉴 정보 삭제4] [REQ_ID : REQ_ADM_041] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(
        new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-045] [TC명 : 메뉴 정보 삭제5] [REQ_ID : REQ_ADM_041] [화면 : 권한 관리 > 메뉴
     * 관리]
     * [기능 : 메뉴 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", null,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "메뉴 삭제 : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            Class<?> type = parameterContext.getParameter().getType();
            // TestCaseDetail만 지원
            return TestCaseDetail.class.isAssignableFrom(type);
          }

          @Override
          public Object resolveParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            return testCase;
          }
        });
      }
    });
  }
}
