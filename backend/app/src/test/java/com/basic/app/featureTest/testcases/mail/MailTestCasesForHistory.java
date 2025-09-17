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
import com.basic.app.dto.requestDto.MailHReqDto;
import com.basic.app.dto.responseDto.MailHResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetailSearchForm;

public class MailTestCasesForHistory implements
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
     * [TC_ID : TC-405] [TC명 : 메일 히스토리 조회1] [REQ_ID : REQ_ADM_058] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 히스토리 조회] [테스트항목 : [N건] 정상 조회] [테스트 상세 : ]
     */

    MailHReqDto td1 = MailHReqDto.builder()
        .mailId("[JUnit]MAIL001")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("createDate").descending());

    // Response
    List<MailHResDto> data1 = List.of(
        MailHResDto.builder()
            .logId("11111111-1111-1111-1111-111111111111")
            .mailId("[JUnit]MAIL001")
            .fromAddr("admin@test.com")
            .toAddr("user1@test.com")
            .title("[JUnit]회원가입을 축하합니다")
            .content("[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>")
            .success("Y")
            .errorMsg(null)
            .build(),
        MailHResDto.builder()
            .logId("11111111-1111-1111-1111-111111111112")
            .mailId("[JUnit]MAIL001")
            .fromAddr("admin@test.com")
            .toAddr("user1@test.com")
            .title("[JUnit]회원가입을 축하합니다")
            .content("[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>")
            .success("Y")
            .errorMsg(null)
            .build());

    PageResponse<MailHResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 2)); // totalElements=1
    pagedData1.setTotalElements(2);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<>(
            BASE_URL + "/history/[JUnit]MAIL001", "정상조회(N건)", td1, ed1, status().isOk(), pr1));

    /*
     * [TC_ID : TC-406] [TC명 : 메일 히스토리 조회2] [REQ_ID : REQ_ADM_058] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 히스토리 조회] [테스트항목 : [N건] 필수값 누락] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetailSearchForm<>("N/A", "필수값 누락", null, null, status().isOk(), pr1));

    /*
     * [TC_ID : TC-407] [TC명 : 메일 히스토리 조회3] [REQ_ID : REQ_ADM_058] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 히스토리 조회] [테스트항목 : [N건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */
    testCases.add(new TestCaseDetailSearchForm<>("N/A", "존재하지 않는 ID 조회", null, null, status().isOk(), pr1));

    /*
     * [TC_ID : TC-408] [TC명 : 메일 히스토리 조회4] [REQ_ID : REQ_ADM_058] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 히스토리 조회] [테스트항목 : [N건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetailSearchForm<>("N/A", "비즈니스로직 케이스", null, null, status().isOk(), pr1));

    /*
     * [TC_ID : TC-409] [TC명 : 메일 히스토리 조회5] [REQ_ID : REQ_ADM_058] [화면 : 시스템 관리 >
     * 메일 관리] [기능 : 메일 히스토리 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetailSearchForm<>(
            BASE_URL + "/history/", "잘못된 형식 입력", td1,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError(), pr1));

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