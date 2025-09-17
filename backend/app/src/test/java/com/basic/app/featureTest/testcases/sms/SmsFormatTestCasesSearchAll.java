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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.PageResponse;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.SmsMReqDto;
import com.basic.app.dto.responseDto.SmsMResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

public class SmsFormatTestCasesSearchAll implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/sms";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<SmsMReqDto, PageResponse<SmsMResDto>>> testCases = new ArrayList<>();

    /*
     * [TC_ID : TC-337] [TC명 : SMS N건 조회1] [REQ_ID : REQ_ADM_067] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 리스트 조회] [테스트항목 : [N건] 정상 조회] [테스트 상세 : ]
     */

    SmsMReqDto td1 = SmsMReqDto.builder().build();
    PageRequest pageRequest1 = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "smsId"));

    List<SmsMResDto> ed1List = List.of(
        SmsMResDto.builder()
            .smsId("[JUnit]SMS001")
            .langType("ko")
            .smsName("[JUnit]회원가입인증")
            .text("[JUnit]회원가입 인증번호는 {code}입니다.")
            .description("[JUnit]회원가입 시 발송되는 인증 SMS")
            .build(),
        SmsMResDto.builder()
            .smsId("[JUnit]SMS002")
            .langType("en")
            .smsName("[JUnit]SignupAuth")
            .text("[JUnit]Your verification code is {code}")
            .description("[JUnit]SMS for user registration verification")
            .build(),
        SmsMResDto.builder()
            .smsId("[JUnit]SMS003")
            .langType("ko")
            .smsName("[JUnit]비밀번호초기화")
            .text("[JUnit]비밀번호 초기화 인증번호는 {code}입니다.")
            .description("[JUnit]비밀번호 초기화 시 발송되는 SMS")
            .build(),
        SmsMResDto.builder()
            .smsId("[JUnit]SMS004")
            .langType("ko")
            .smsName("[JUnit]결제알림")
            .text("[JUnit]{amount}원 결제가 완료되었습니다.")
            .description("[JUnit]결제 완료 시 발송되는 알림 SMS")
            .build(),
        SmsMResDto.builder()
            .smsId("[JUnit]SMS005")
            .langType("ko")
            .smsName("[JUnit]주문확인")
            .text("[JUnit]주문번호 {orderNo}의 주문이 확인되었습니다.")
            .description("[JUnit]주문 확인 시 발송되는 SMS")
            .build(),
        SmsMResDto.builder()
            .smsId("[JUnit]SMS006")
            .langType("ko")
            .smsName("[JUnit]배송알림")
            .text("[JUnit]택배가 배송 시작되었습니다. 운송장번호: {trackingNo}")
            .description("[JUnit]배송 시작 시 발송되는 알림 SMS")
            .build());

    PageResponse<SmsMResDto> ed1 = new PageResponse<>(new PageImpl<>(ed1List, pageRequest1, 6L));

    testCases.add(
        new TestCaseDetailSearchForm<>(BASE_URL + "/search", "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk(), pageRequest1));

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
            return parameterContext.getParameter().getType().equals(TestCaseDetailSearchForm.class);
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