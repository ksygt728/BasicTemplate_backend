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
import com.basic.app.dto.responseDto.MailMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class MailTestCasesForSearch implements
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
     * [TC_ID : TC-400] [TC명 : 메일 단건 조회1] [REQ_ID : REQ_ADM_057_2] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 단건 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    String td1 = "[JUnit]MAIL001";
    MailMResDto ed1 = MailMResDto.builder()
        .mailId("[JUnit]MAIL001")
        .langType("ko")
        .mailName("[JUnit]회원가입메일")
        .title("[JUnit]회원가입을 축하합니다")
        .content("[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>")
        .description("[JUnit]회원가입 완료 시 발송되는 메일")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]MAIL001", "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-401] [TC명 : 메일 단건 조회2] [REQ_ID : REQ_ADM_057_2] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 단건 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "필수값 누락", null, null, status().isOk()));

    /*
     * [TC_ID : TC-402] [TC명 : 메일 단건 조회3] [REQ_ID : REQ_ADM_057_2] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 단건 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]MAIL001_NOT_EXIST", "존재하지 않는 ID 조회", "MAIL001_NOT_EXIST",
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));
    /*
     * [TC_ID : TC-403] [TC명 : 메일 단건 조회4] [REQ_ID : REQ_ADM_057_2] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 단건 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-404] [TC명 : 메일 단건 조회5] [REQ_ID : REQ_ADM_057_2] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 단건 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
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