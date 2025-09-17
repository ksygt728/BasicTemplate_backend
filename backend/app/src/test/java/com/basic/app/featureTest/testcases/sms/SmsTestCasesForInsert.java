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

public class SmsTestCasesForInsert implements
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
     * [TC_ID : TC-347] [TC명 : SMS 추가1] [REQ_ID : REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
     */

    SmsMReqDto td1 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS_NEW001")
        .langType("ko")
        .smsName("[JUnit]새로운SMS")
        .text("[JUnit]새로운 SMS 내용입니다.")
        .description("[JUnit]새로운 SMS 설명")
        .build();

    SmsMResDto ed1 = SmsMResDto.builder()
        .smsId("[JUnit]SMS_NEW001")
        .langType("ko")
        .smsName("[JUnit]새로운SMS")
        .text("[JUnit]새로운 SMS 내용입니다.")
        .description("[JUnit]새로운 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 등록", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-348] [TC명 : SMS 추가2] [REQ_ID : REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    // smsId 누락
    SmsMReqDto td2_1 = SmsMReqDto.builder()
        .langType("ko")
        .smsName("[JUnit]새로운SMS")
        .text("[JUnit]새로운 SMS 내용입니다.")
        .description("[JUnit]새로운 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - smsId", td2_1,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "SMS아이디는 필수입니다."), status().is4xxClientError()));

    // langType 누락
    SmsMReqDto td2_2 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS_NEW002")
        .smsName("[JUnit]새로운SMS")
        .text("[JUnit]새로운 SMS 내용입니다.")
        .description("[JUnit]새로운 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - langType", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어타입은 필수입니다."), status().is4xxClientError()));

    // smsName 누락
    SmsMReqDto td2_3 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS_NEW003")
        .langType("ko")
        .text("[JUnit]새로운 SMS 내용입니다.")
        .description("[JUnit]새로운 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - smsName", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "템플릿명은 필수입니다."), status().is4xxClientError()));

    // text 누락
    SmsMReqDto td2_4 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS_NEW004")
        .langType("ko")
        .smsName("[JUnit]새로운SMS")
        .description("[JUnit]새로운 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - text", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "SMS내용은 필수입니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-349] [TC명 : SMS 추가3] [REQ_ID : REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
     */
    SmsMReqDto td3 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS001") // 이미 존재하는 ID
        .langType("ko")
        .smsName("[JUnit]중복SMS")
        .text("[JUnit]중복 SMS 내용입니다.")
        .description("[JUnit]중복 SMS 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하는 ID 등록 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED), status().is4xxClientError()));

    /*
     * [TC_ID : TC-350] [TC명 : SMS 추가4] [REQ_ID : REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    SmsMReqDto td4 = SmsMReqDto.builder()
        .smsId("[JUnit]SMS_BIZ001")
        .langType("en")
        .smsName("[JUnit]BusinessSMS")
        .text("[JUnit]Business SMS content with {variable}")
        .description("[JUnit]Business logic test SMS")
        .build();

    SmsMResDto ed4 = SmsMResDto.builder()
        .smsId("[JUnit]SMS_BIZ001")
        .langType("en")
        .smsName("[JUnit]BusinessSMS")
        .text("[JUnit]Business SMS content with {variable}")
        .description("[JUnit]Business logic test SMS")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "비즈니스로직 케이스", td4,
            ResponseApi.success(Map.of("data", ed4)), status().isOk()));

    /*
     * [TC_ID : TC-351] [TC명 : SMS 추가5] [REQ_ID : REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송
     * 로그]
     * [기능 : SMS 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
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