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
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class BbsTestCasesForSearch implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/bbs";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-397] [TC명 : 게시글 상세 조회1] [REQ_ID : REQ_ADM_082] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 상세 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */
    String td1 = "[JUnit]BBS001";

    UserResDto writor1 = UserResDto.builder()
        .userId("[JUnit]admin001")
        .name("[JUnit]김관리")
        .phoneNum("010-1111-1111")
        .email("admin001@company.com")
        .role("ROLE_GUEST")
        .userType("CBMS")
        .gender("M")
        .department(
            DepartmentResDto.builder()
                .deptCode("[JUnit]21323243")
                .deptNm("TEST회사")
                .company(CompanyResDto.builder()
                    .companyCode("[JUnit]C100")
                    .companyName("CBMS회사")
                    .build())
                .upperDeptCode("20000000")
                .deptLv(1)
                .useYn("Y")
                .build())
        .build();

    BbsResDto ed1 = BbsResDto.builder()
        .bbsId("[JUnit]BBS001")
        .bbsType("[JUnit]공지")
        .title("[JUnit]공지사항 제목1")
        .content("[JUnit]공지사항 내용입니다.")
        .writor(writor1)
        .writeDate(LocalDateTime.now()) // 실제 테스트에서는 DB에서 조회된 값과 매칭
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]BBS001", "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-398] [TC명 : 게시글 상세 조회2] [REQ_ID : REQ_ADM_082] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 상세 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */
    // 해당 케이스 미존재 (PathVariable이므로 필수값 누락 불가)
    testCases.add(new TestCaseDetail<>("N/A", "필수값 누락", null, null, status().isOk()));

    /*
     * [TC_ID : TC-399] [TC명 : 게시글 상세 조회3] [REQ_ID : REQ_ADM_082] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 상세 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]BBS001_NOT_EXIST", "존재하지 않는 ID 조회", "BBS001_NOT_EXIST",
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-400] [TC명 : 게시글 상세 조회4] [REQ_ID : REQ_ADM_082] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 상세 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 삭제된 게시글 조회 시도
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-401] [TC명 : 게시글 상세 조회5] [REQ_ID : REQ_ADM_082] [화면 : 시스템 관리 > 게시판
     * 관리] [기능 : 게시판 상세 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력",
            "", ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "게시글 조회 : " + (testCase.getTestName());
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