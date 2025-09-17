package com.basic.app.featureTest.testcases.mail;

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

public class MailTestCasesForDelete implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/mail";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-434] [TC명 : 메일 삭제1] [REQ_ID : REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
     */

    String td1 = "[JUnit]MAIL001";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]MAIL001", "정상 삭제", td1,
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-435] [TC명 : 메일 삭제2] [REQ_ID : REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
     */
    String td2 = "[JUnit]MAIL_DELETED";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]MAIL_DELETED", "이미 삭제한 항목 삭제 시도", td2,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-436] [TC명 : 메일 삭제3] [REQ_ID : REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
     */
    String td3 = "[JUnit]MAIL001_NOT_EXIST";

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]MAIL001_NOT_EXIST", "존재하지 않는 ID 삭제 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-437] [TC명 : 메일 삭제4] [REQ_ID : REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-438] [TC명 : 메일 삭제5] [REQ_ID : REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", "",
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return testCase.getTestName();
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.getParameter().getType() == TestCaseDetail.class;
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