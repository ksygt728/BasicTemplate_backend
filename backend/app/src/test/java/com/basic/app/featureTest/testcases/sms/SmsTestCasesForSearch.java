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
import com.basic.app.dto.responseDto.SmsMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class SmsTestCasesForSearch implements
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
     * [TC_ID : TC-327] [TC명 : SMS 리스트 조회1] [REQ_ID : REQ_ADM_067_2] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 단건 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    String td1 = "[JUnit]SMS001";
    SmsMResDto ed1 = SmsMResDto.builder()
        .smsId("[JUnit]SMS001")
        .langType("ko")
        .smsName("[JUnit]회원가입인증")
        .text("[JUnit]회원가입 인증번호는 {code}입니다.")
        .description("[JUnit]회원가입 시 발송되는 인증 SMS")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td1, "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-328] [TC명 : SMS 리스트 조회2] [REQ_ID : REQ_ADM_067_2] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 단건 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */

    String td2 = "";
    testCases.add(
        new TestCaseDetail<>("N/A", "필수값 누락", td2,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-329] [TC명 : SMS 리스트 조회3] [REQ_ID : REQ_ADM_067_2] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 단건 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */

    String td3 = "NONEXISTENT_SMS";
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td3, "존재하지 않는 ID 조회", td3,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-330] [TC명 : SMS 리스트 조회4] [REQ_ID : REQ_ADM_067_2] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 단건 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */

    String td4 = "[JUnit]SMS002";
    SmsMResDto ed4 = SmsMResDto.builder()
        .smsId("[JUnit]SMS002")
        .langType("en")
        .smsName("[JUnit]SignupAuth")
        .text("[JUnit]Your verification code is {code}")
        .description("[JUnit]SMS for user registration verification")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td4, "비즈니스로직 케이스", td4,
            ResponseApi.success(Map.of("data", ed4)), status().isOk()));

    /*
     * [TC_ID : TC-331] [TC명 : SMS 리스트 조회5] [REQ_ID : REQ_ADM_067_2] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 단건 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    String td5 = "INVALID_FORMAT_!@#";
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + td5, "잘못된 형식 입력", td5,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

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