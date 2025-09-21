package com.basic.app.featureTest.testcases.bbs;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class BbsTestCasesForInsert implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/bbs";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-407] [TC명 : 게시글 추가1] [REQ_ID : REQ_ADM_083] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
     */
    BbsReqDto td1 = BbsReqDto.builder()
        .bbsType("[JUnit]신규")
        .title("[JUnit]신규 게시글 제목")
        .content("[JUnit]신규 게시글 내용입니다.")
        .build();

    UserResDto writor1 = UserResDto.builder()
        .userId("ADMIN")
        .name("김승연")
        .phoneNum("01091360767")
        .email("ksygt7280@naver.com")
        .role("ROLE_ADMIN")
        .userType("CBMS")
        .gender("M")
        .department(DepartmentResDto.builder()
            .deptCode("20000000")
            .deptNm("CBSK")
            .upperDeptCode("ROOT")
            .deptLv(0)
            .company(CompanyResDto.builder()
                .companyCode("C100")
                .companyName("CBSK회사")
                .build())
            .useYn("Y")
            .build())
        .build();

    BbsResDto ed1 = BbsResDto.builder()
        .bbsType("[JUnit]신규")
        .title("[JUnit]신규 게시글 제목")
        .content("[JUnit]신규 게시글 내용입니다.")
        .writor(writor1)
        .writeDate(LocalDateTime.now())
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 등록", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-408] [TC명 : 게시글 추가2] [REQ_ID : REQ_ADM_083] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */
    // bbsType 누락
    BbsReqDto td2_1 = BbsReqDto.builder()
        .title("[JUnit]제목")
        .content("[JUnit]내용")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - bbsType", td2_1,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "게시판타입은 필수입니다."), status().is4xxClientError()));

    // title 누락
    BbsReqDto td2_2 = BbsReqDto.builder()
        .bbsType("[JUnit]공지")
        .content("[JUnit]내용")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - title", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "제목을 입력하세요."), status().is4xxClientError()));

    // content 누락
    BbsReqDto td2_3 = BbsReqDto.builder()
        .bbsType("[JUnit]공지")
        .title("[JUnit]제목")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - content", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "내용을 입력하세요."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-409] [TC명 : 게시글 추가3] [REQ_ID : REQ_ADM_083] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
     */
    // 해당 케이스 미존재 (bbsId는 자동 채번)
    testCases.add(new TestCaseDetail<>("N/A", "존재하는 ID 등록 시도", null, null, status().isOk()));

    /*
     * [TC_ID : TC-410] [TC명 : 게시글 추가4] [REQ_ID : REQ_ADM_083] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-411] [TC명 : 게시글 추가5] [REQ_ID : REQ_ADM_083] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td1,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "게시글 추가 : " + (testCase.getTestName());
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