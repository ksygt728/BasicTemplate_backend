package com.basic.app.featureTest.testcases.mulLang;

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
 * @파일명 : MulLangTestCasesForDelete.java
 * @설명 : 다국어 정보 삭제 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.09.10
 * @변경이력 :
 *       2025.09.10 김승연 최초 생성
 */
public class MulLangTestCasesForDelete implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/lang";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();
    /*
     * [TC_ID : TC-177] [TC명 : 다국어 삭제1] [REQ_ID : REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
     */

    String langGubun = "msg";
    String langCd = "[JUnit]TEST002";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + langGubun + "/" + langCd, "정상 삭제",
            Map.of("langGubun", langGubun, "langCd", langCd),
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-178] [TC명 : 다국어 삭제2] [REQ_ID : REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/msg/[JUnit]CODE001", "이미 삭제한 항목 삭제 시도",
            Map.of("langGubun", "msg", "langCd", "[JUnit]CODE001"),
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-179] [TC명 : 다국어 삭제3] [REQ_ID : REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/msg/[JUnit]CODE001_NOT_EXIST", "존재하지 않는 ID 삭제 시도",
            Map.of("langGubun", "msg", "langCd", "[JUnit]CODE001_NOT_EXIST"),
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-180] [TC명 : 다국어 삭제4] [REQ_ID : REQ_ADM_037_2] [화면 : 기준 정보 > 다국어
     * 관리]
     * [기능 : 다국어 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : 다국어 삭제 각 언어별 상세 결과]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/ko" + "/label" + "/[JUnit]LABEL001", "다국어 삭제 각 언어별 상세 결과",
            Map.of("langType", "ko", "langGubun", "label", "langCd", "[JUnit]LABEL001"),
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-181] [TC명 : 다국어 삭제5] [REQ_ID : REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "//", "잘못된 형식 입력",
            null,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "다국어 삭제 : " + (testCase.getTestName());
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
