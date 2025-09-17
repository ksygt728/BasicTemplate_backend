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
import com.basic.app.dto.requestDto.MailMReqDto;
import com.basic.app.dto.responseDto.MailMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class MailTestCasesForInsert implements
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
     * [TC_ID : TC-420] [TC명 : 메일 추가1] [REQ_ID : REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
     */

    MailMReqDto td1 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL_NEW001")
        .langType("ko")
        .mailName("[JUnit]새로운메일")
        .title("[JUnit]새로운 메일 제목")
        .content("[JUnit]새로운 메일 내용입니다.")
        .description("[JUnit]새로운 메일 설명")
        .build();

    MailMResDto ed1 = MailMResDto.builder()
        .mailId("[JUnit]MAIL_NEW001")
        .langType("ko")
        .mailName("[JUnit]새로운메일")
        .title("[JUnit]새로운 메일 제목")
        .content("[JUnit]새로운 메일 내용입니다.")
        .description("[JUnit]새로운 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 등록", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-421] [TC명 : 메일 추가2] [REQ_ID : REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    // mailId 누락
    MailMReqDto td2_1 = MailMReqDto.builder()
        .langType("ko")
        .mailName("[JUnit]새로운메일")
        .title("[JUnit]새로운 메일 제목")
        .content("[JUnit]새로운 메일 내용입니다.")
        .description("[JUnit]새로운 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - mailId", td2_1,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메일아이디는 필수입니다."), status().is4xxClientError()));

    // langType 누락
    MailMReqDto td2_2 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL_NEW002")
        .mailName("[JUnit]새로운메일")
        .title("[JUnit]새로운 메일 제목")
        .content("[JUnit]새로운 메일 내용입니다.")
        .description("[JUnit]새로운 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - langType", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어타입은 필수입니다."), status().is4xxClientError()));

    // mailName 누락
    MailMReqDto td2_3 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL_NEW003")
        .langType("ko")
        .title("[JUnit]새로운 메일 제목")
        .content("[JUnit]새로운 메일 내용입니다.")
        .description("[JUnit]새로운 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - mailName", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메일명은 필수입니다."), status().is4xxClientError()));

    // title 누락
    MailMReqDto td2_4 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL_NEW004")
        .langType("ko")
        .mailName("[JUnit]새로운메일")
        .content("[JUnit]새로운 메일 내용입니다.")
        .description("[JUnit]새로운 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - title", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "제목은 필수입니다."), status().is4xxClientError()));

    // content 누락
    MailMReqDto td2_5 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL_NEW005")
        .langType("ko")
        .mailName("[JUnit]새로운메일")
        .title("[JUnit]새로운 메일 제목")
        .description("[JUnit]새로운 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - content", td2_5,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "내용은 필수입니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-422] [TC명 : 메일 추가3] [REQ_ID : REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
     */
    MailMReqDto td3 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL001") // 이미 존재하는 ID
        .langType("ko")
        .mailName("[JUnit]중복메일")
        .title("[JUnit]중복 메일 제목")
        .content("[JUnit]중복 메일 내용입니다.")
        .description("[JUnit]중복 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하는 ID 등록 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED), status().is4xxClientError()));

    /*
     * [TC_ID : TC-423] [TC명 : 메일 추가4] [REQ_ID : REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-424] [TC명 : 메일 추가5] [REQ_ID : REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    MailMReqDto td4 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL001") // 이미 존재하는 ID
        .langType("ko")
        .mailName("[JUnit]중복메일")
        .title("[JUnit]중복 메일 제목")
        .content("[JUnit]중복 메일 내용입니다.")
        .description("[JUnit]중복 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td4,
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