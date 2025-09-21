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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.PageResponse;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.MailMReqDto;
import com.basic.app.dto.responseDto.MailMResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

public class MailFormatTestCasesSearchAll implements
    TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/mail";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<?, ?>> testCases = new ArrayList<TestCaseDetailSearchForm<?, ?>>();
    /*
     * [TC_ID : TC-410] [TC명 : 메일 리스트 조회1] [REQ_ID : REQ_ADM_057] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 리스트 조회] [테스트항목 : [N건] 정상 조회] [테스트 상세 : ]
     */

    MailMReqDto td1 = MailMReqDto.builder()
        .mailId("[JUnit]MAIL")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("mailId").ascending());

    // Response
    List<MailMResDto> data1 = List.of(
        MailMResDto.builder()
            .mailId("[JUnit]MAIL001")
            .langType("ko")
            .mailName("[JUnit]회원가입메일")
            .title("[JUnit]회원가입을 축하합니다")
            .content("[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>")
            .description("[JUnit]회원가입 완료 시 발송되는 메일")
            .build(),
        MailMResDto.builder()
            .mailId("[JUnit]MAIL002")
            .langType("en")
            .mailName("[JUnit]SignupMail")
            .title("[JUnit]Welcome! Registration Complete")
            .content("[JUnit]<h1>Welcome!</h1><p>Thank you for signing up.</p>")
            .description("[JUnit]Welcome email for new users")
            .build(),
        MailMResDto.builder()
            .mailId("[JUnit]MAIL003")
            .langType("ko")
            .mailName("[JUnit]비밀번호초기화메일")
            .title("[JUnit]비밀번호가 초기화되었습니다")
            .content("[JUnit]<h1>비밀번호 초기화</h1><p>비밀번호가 초기화되었습니다. 새로운 비밀번호로 로그인해주세요.</p>")
            .description("[JUnit]비밀번호 초기화 시 발송되는 메일")
            .build(),
        MailMResDto.builder()
            .mailId("[JUnit]MAIL004")
            .langType("ko")
            .mailName("[JUnit]승인완료메일")
            .title("[JUnit]승인이 완료되었습니다")
            .content("[JUnit]<h1>승인 완료</h1><p>요청하신 내용이 승인되었습니다.</p>")
            .description("[JUnit]승인 완료 시 발송되는 메일")
            .build(),
        MailMResDto.builder()
            .mailId("[JUnit]MAIL005")
            .langType("ko")
            .mailName("[JUnit]공지사항메일")
            .title("[JUnit]중요 공지사항입니다")
            .content("[JUnit]<h1>공지사항</h1><p>중요한 공지사항이 있습니다. 확인해주세요.</p>")
            .description("[JUnit]공지사항 발송용 메일")
            .build(),
        MailMResDto.builder()
            .mailId("[JUnit]MAIL006")
            .langType("ko")
            .mailName("[JUnit]이벤트메일")
            .title("[JUnit]특별 이벤트에 참여하세요")
            .content("[JUnit]<h1>특별 이벤트</h1><p>특별한 이벤트에 참여해보세요!</p>")
            .description("[JUnit]이벤트 안내 메일")
            .build());

    PageResponse<MailMResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 6)); // totalElements=6
    pagedData1.setTotalElements(6);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<>(
            BASE_URL + "/search", "정상조회(N건)", td1, ed1, status().isOk(), pr1));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "메일 조회(N건) : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.getParameter().getType() == TestCaseDetailSearchForm.class;
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