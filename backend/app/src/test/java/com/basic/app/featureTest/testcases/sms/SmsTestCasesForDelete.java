package com.basic.app.featureTest.testcases.sms;

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

public class SmsTestCasesForDelete implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/sms";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-357] [TC명 : SMS 삭제1] [REQ_ID : REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
     */

    String td1 = "[JUnit]SMS001";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td1, "정상 삭제", td1,
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-358] [TC명 : SMS 삭제2] [REQ_ID : REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
     */

    String td2 = "[JUnit]SMS005"; // 이미 위에서 삭제한 항목

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td2, "이미 삭제한 항목 삭제 시도", td2,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-359] [TC명 : SMS 삭제3] [REQ_ID : REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
     */

    String td3 = "NONEXISTENT_SMS";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td3, "존재하지 않는 ID 삭제 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-360] [TC명 : SMS 삭제4] [REQ_ID : REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    String td4 = "[JUnit]SMS006";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td4, "비즈니스로직 케이스", td4,
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-361] [TC명 : SMS 삭제5] [REQ_ID : REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    String td5 = "INVALID_FORMAT_!@#";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td5, "잘못된 형식 입력", td5,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "문자 삭제 : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.getParameter().getType().equals(TestCaseDetail.class);
          }

          @Override
          public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return testCase;
          }
        });
      }
    });
  }
}