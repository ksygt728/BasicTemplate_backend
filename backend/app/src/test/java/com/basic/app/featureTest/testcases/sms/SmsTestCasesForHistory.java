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
import com.basic.app.dto.responseDto.SmsHResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetailSearchForm;

public class SmsTestCasesForHistory implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/sms";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<?, ?>> testCases = new ArrayList<TestCaseDetailSearchForm<?, ?>>();

    /*
     * [TC_ID : TC-332] [TC명 : SMS 이력 조회1] [REQ_ID : REQ_ADM_068] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 이력 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    String td1 = "[JUnit]SMS001";
    PageRequest pageRequest1 = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"));

    List<SmsHResDto> ed1List = List.of(
        SmsHResDto.builder()
            .logId("11111111-1111-1111-1111-111111111111")
            .smsId("[JUnit]SMS001")
            .fromPhone("01012345678")
            .toPhone("01098765432")
            .text("[JUnit]회원가입 인증번호는 123456입니다.")
            .success("Y")
            .errorMsg(null)
            .build(),
        SmsHResDto.builder()
            .logId("11111111-1111-1111-1111-111111111112")
            .smsId("[JUnit]SMS001")
            .fromPhone("01012345678")
            .toPhone("01098765432")
            .text("[JUnit]회원가입 인증번호는 654321입니다.")
            .success("Y")
            .errorMsg(null)
            .build());

    PageResponse<SmsHResDto> ed1 = new PageResponse<>(new PageImpl<>(ed1List, pageRequest1, 2L));

    testCases.add(
        new TestCaseDetailSearchForm<>(BASE_URL + "/history/" + td1, "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk(), pageRequest1));

    /*
     * [TC_ID : TC-333] [TC명 : SMS 이력 조회2] [REQ_ID : REQ_ADM_068] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 이력 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */

    String td2 = "";
    PageRequest pageRequest2 = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"));

    testCases.add(
        new TestCaseDetailSearchForm<>("N/A", "필수값 누락", td2,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError(), pageRequest2));

    /*
     * [TC_ID : TC-334] [TC명 : SMS 이력 조회3] [REQ_ID : REQ_ADM_068] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 이력 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */

    String td3 = "NONEXISTENT_SMS";
    PageRequest pageRequest3 = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"));

    PageResponse<SmsHResDto> ed3 = new PageResponse<>(new PageImpl<>(List.of(), pageRequest3, 0L));

    testCases.add(
        new TestCaseDetailSearchForm<>(BASE_URL + "/history/" + td3, "존재하지 않는 ID 조회", td3,
            ResponseApi.success(Map.of("data", ed3)), status().isOk(), pageRequest3));

    /*
     * [TC_ID : TC-335] [TC명 : SMS 이력 조회4] [REQ_ID : REQ_ADM_068] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 이력 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */

    String td4 = "[JUnit]SMS003";
    PageRequest pageRequest4 = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"));

    List<SmsHResDto> ed4List = List.of(
        SmsHResDto.builder()
            .logId("33333333-3333-3333-3333-333333333333")
            .smsId("[JUnit]SMS003")
            .fromPhone("01012345678")
            .toPhone("01076543210")
            .text("[JUnit]비밀번호 초기화 인증번호는 345678입니다.")
            .success("N")
            .errorMsg("[JUnit]SMS 발송 실패")
            .build());

    PageResponse<SmsHResDto> ed4 = new PageResponse<>(new PageImpl<>(ed4List, pageRequest4, 1L));

    testCases.add(
        new TestCaseDetailSearchForm<>(BASE_URL + "/history/" + td4, "비즈니스로직 케이스", td4,
            ResponseApi.success(Map.of("data", ed4)), status().isOk(), pageRequest4));

    /*
     * [TC_ID : TC-336] [TC명 : SMS 이력 조회5] [REQ_ID : REQ_ADM_068] [화면 : 시스템 관리 >
     * SMS 발송 로그] [기능 : SMS 이력 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    String td5 = "INVALID_FORMAT_!@#";
    PageRequest pageRequest5 = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"));

    PageResponse<SmsHResDto> ed5 = new PageResponse<>(new PageImpl<>(List.of(), pageRequest5, 0L));

    testCases.add(
        new TestCaseDetailSearchForm<>(BASE_URL + "/history/" + td5, "잘못된 형식 입력", td5,
            ResponseApi.success(Map.of("data", ed5)), status().isOk(), pageRequest5));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "문자 이력 조회 : " + (testCase.getTestName());
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