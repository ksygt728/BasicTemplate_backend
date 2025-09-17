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

public class MailTestCasesForUpdate implements
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
     * [TC_ID : TC-427] [TC명 : 메일 수정1] [REQ_ID : REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
     */

    MailMReqDto td1 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL001")
        .langType("ko")
        .mailName("[JUnit]회원가입메일_수정")
        .title("[JUnit]회원가입을 축하합니다_수정")
        .content("[JUnit]<h1>회원가입 축하_수정</h1><p>안녕하세요. 회원가입을 축하드립니다_수정.</p>")
        .description("[JUnit]회원가입 완료 시 발송되는 메일_수정")
        .build();

    MailMResDto ed1 = MailMResDto.builder()
        .mailId("[JUnit]MAIL001")
        .langType("ko")
        .mailName("[JUnit]회원가입메일_수정")
        .title("[JUnit]회원가입을 축하합니다_수정")
        .content("[JUnit]<h1>회원가입 축하_수정</h1><p>안녕하세요. 회원가입을 축하드립니다_수정.</p>")
        .description("[JUnit]회원가입 완료 시 발송되는 메일_수정")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-428] [TC명 : 메일 수정2] [REQ_ID : REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    // mailId 누락
    MailMReqDto td2_1 = MailMReqDto.builder()
        .langType("ko")
        .mailName("[JUnit]수정메일")
        .title("[JUnit]수정 메일 제목")
        .content("[JUnit]수정 메일 내용입니다.")
        .description("[JUnit]수정 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - mailId", td2_1,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메일아이디는 필수입니다."), status().is4xxClientError()));

    // langType 누락
    MailMReqDto td2_2 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL002")
        .mailName("[JUnit]수정메일")
        .title("[JUnit]수정 메일 제목")
        .content("[JUnit]수정 메일 내용입니다.")
        .description("[JUnit]수정 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - langType", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어타입은 필수입니다."), status().is4xxClientError()));

    // mailName 누락
    MailMReqDto td2_3 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL003")
        .langType("ko")
        .title("[JUnit]수정 메일 제목")
        .content("[JUnit]수정 메일 내용입니다.")
        .description("[JUnit]수정 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - mailName", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "메일명은 필수입니다."), status().is4xxClientError()));

    // title 누락
    MailMReqDto td2_4 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL004")
        .langType("ko")
        .mailName("[JUnit]수정메일")
        .content("[JUnit]수정 메일 내용입니다.")
        .description("[JUnit]수정 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - title", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "제목은 필수입니다."), status().is4xxClientError()));

    // content 누락
    MailMReqDto td2_5 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL005")
        .langType("ko")
        .mailName("[JUnit]수정메일")
        .title("[JUnit]수정 메일 제목")
        .description("[JUnit]수정 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 오류 - content", td2_5,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "내용은 필수입니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-429] [TC명 : 메일 수정3] [REQ_ID : REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
     */
    MailMReqDto td3 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL001_NOT_EXIST")
        .langType("ko")
        .mailName("[JUnit]존재하지않는메일")
        .title("[JUnit]존재하지 않는 메일 제목")
        .content("[JUnit]존재하지 않는 메일 내용입니다.")
        .description("[JUnit]존재하지 않는 메일 설명")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-430] [TC명 : 메일 수정4] [REQ_ID : REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-431] [TC명 : 메일 수정5] [REQ_ID : REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리]
     * [기능 : 메일 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    MailMReqDto td4 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL001_NOT_EXIST")
        .langType("ko")
        .mailName("[JUnit]존재하지않는메일")
        .title("[JUnit]존재하지 않는 메일 제목")
        .content("[JUnit]존재하지 않는 메일 내용입니다.")
        .description("[JUnit]존재하지 않는 메일 설명")
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