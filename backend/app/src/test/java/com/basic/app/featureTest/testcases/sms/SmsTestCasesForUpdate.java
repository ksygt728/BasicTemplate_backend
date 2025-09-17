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
import com.basic.app.dto.requestDto.SmsMReqDto;
import com.basic.app.dto.responseDto.SmsMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class SmsTestCasesForUpdate implements
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
     * [TC_ID : TC-352] [TC명 : SMS 수정1] [REQ_ID : REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
     */

    SmsMReqDto td1 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS001")
        .langType("ko")
        .smsName("[JUnit]회원가입인증_수정")
        .text("[JUnit]수정된 회원가입 인증번호는 {code}입니다.")
        .description("[JUnit]수정된 회원가입 시 발송되는 인증 SMS")
        .build();

    SmsMResDto ed1 = SmsMResDto.builder()
        .smsId("[JUnit]SMS001")
        .langType("ko")
        .smsName("[JUnit]회원가입인증_수정")
        .text("[JUnit]수정된 회원가입 인증번호는 {code}입니다.")
        .description("[JUnit]수정된 회원가입 시 발송되는 인증 SMS")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-353] [TC명 : SMS 수정2] [REQ_ID : REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    // smsId 누락
    SmsMReqDto td2_1 = SmsMReqDto.builder()
        .langType("ko")
        .smsName("[JUnit]수정SMS")
        .text("[JUnit]수정된 SMS 내용입니다.")
        .description("[JUnit]수정된 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - smsId", td2_1,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "SMS아이디는 필수입니다."), status().is4xxClientError()));

    // langType 누락
    SmsMReqDto td2_2 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS002")
        .smsName("[JUnit]수정SMS")
        .text("[JUnit]수정된 SMS 내용입니다.")
        .description("[JUnit]수정된 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - langType", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어타입은 필수입니다."), status().is4xxClientError()));

    // smsName 누락
    SmsMReqDto td2_3 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS003")
        .langType("ko")
        .text("[JUnit]수정된 SMS 내용입니다.")
        .description("[JUnit]수정된 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - smsName", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "템플릿명은 필수입니다."), status().is4xxClientError()));

    // text 누락
    SmsMReqDto td2_4 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS004")
        .langType("ko")
        .smsName("[JUnit]수정SMS")
        .description("[JUnit]수정된 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - text", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "SMS내용은 필수입니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-354] [TC명 : SMS 수정3] [REQ_ID : REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
     */

    SmsMReqDto td3 = SmsMReqDto.builder()
        .smsId("NONEXISTENT_SMS")
        .langType("ko")
        .smsName("[JUnit]존재하지않는SMS")
        .text("[JUnit]존재하지 않는 SMS 내용입니다.")
        .description("[JUnit]존재하지 않는 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-355] [TC명 : SMS 수정4] [REQ_ID : REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    SmsMReqDto td4 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS002")
        .langType("en")
        .smsName("[JUnit]UpdatedSignupAuth")
        .text("[JUnit]Your updated verification code is {code}")
        .description("[JUnit]Updated SMS for user registration verification")
        .build();

    SmsMResDto ed4 = SmsMResDto.builder()
        .smsId("[JUnit]SMS002")
        .langType("en")
        .smsName("[JUnit]UpdatedSignupAuth")
        .text("[JUnit]Your updated verification code is {code}")
        .description("[JUnit]Updated SMS for user registration verification")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "비즈니스로직 케이스", td4,
            ResponseApi.success(Map.of("data", ed4)), status().isOk()));

    /*
     * [TC_ID : TC-356] [TC명 : SMS 수정5] [REQ_ID : REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    SmsMReqDto td5 = SmsMReqDto.builder()
        .smsId("") // 빈 문자열
        .langType("")
        .smsName("")
        .text("")
        .description("")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td5,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "문자 수정 : " + (testCase.getTestName());
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