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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.PageResponse;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

/**
 * @파일명 : BbsTestCasesForSearchAll.java
 * @설명 : 게시글 목록조회 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.09.20
 * @변경이력 :
 *       2025.09.20 김승연 최초 생성
 */
public class BbsTestCasesForSearchAll implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/bbs";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<?, ?>> testCases = new ArrayList<TestCaseDetailSearchForm<?, ?>>();

    /*
     * [TC_ID : TC-397] [TC명 : 게시글 리스트 조회1] [REQ_ID : REQ_ADM_081] [화면 : 시스템 관리 >
     * 게시판 관리] [기능 : 게시판 리스트 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */
    BbsReqDto td1 = BbsReqDto.builder()
        .bbsType("[JUnit]공지")
        .build();

    PageRequest pr1 = PageRequest.of(0, 100, Sort.by("writeDate").descending());

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

    // 실제 검색 조건에 맞는 결과
    List<BbsResDto> data1 = List.of(
        BbsResDto.builder()
            .bbsId("[JUnit]BBS001")
            .bbsType("[JUnit]공지")
            .title("[JUnit]공지사항 제목1")
            .content("[JUnit]공지사항 내용입니다.")
            .writor(writor1)
            .writeDate(LocalDateTime.now())
            .build(),
        BbsResDto.builder()
            .bbsId("[JUnit]BBS007")
            .bbsType("[JUnit]공지")
            .title("[JUnit]중요공지 제목7")
            .content("[JUnit]중요공지 내용입니다.")
            .writor(writor1)
            .writeDate(LocalDateTime.now())
            .build());

    PageResponse<BbsResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 2));
    pagedData1.setTotalElements(2);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(new TestCaseDetailSearchForm<>(BASE_URL + "/search", "정상 조회", td1, ed1, status().isOk(), pr1));

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