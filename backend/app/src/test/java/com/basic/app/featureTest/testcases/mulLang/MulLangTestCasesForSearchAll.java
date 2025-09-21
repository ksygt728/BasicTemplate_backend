package com.basic.app.featureTest.testcases.mulLang;

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
import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TestCaseDetailSearchForm;

/**
 * @파일명 : MulLangTestCasesForSearchAll.java
 * @설명 : 다국어 정보 목록조회 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.09.10
 * @변경이력 :
 *       2025.09.10 김승연 최초 생성
 */
public class MulLangTestCasesForSearchAll implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/lang";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<?, ?>> testCases = new ArrayList<TestCaseDetailSearchForm<?, ?>>();

    /*
     * [TC_ID : TC-157] [TC명 : 다국어 리스트 조회1] [REQ_ID : REQ_ADM_033] [화면 : 기준 정보 > 다국어
     * 관리] [기능 : 다국어 리스트 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */
    MulLangReqDto td1 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        .langGubun("msg")
        .useYn("Y")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("langCd").ascending());

    // 실제 검색 조건에 맞는 결과 (정확히 일치하는 1건만 반환됨)
    List<MulLangResDto> data1 = List.of(
        MulLangResDto.builder()
            .langCd("[JUnit]TEST001")
            .langType("ko")
            .langGubun("msg")
            .langNm("[JUnit]테스트 메시지 1")
            .useYn("Y")
            .build());

    PageResponse<MulLangResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 1)); // totalElements=1 (실제 검색 결과에 맞춰 수정)
    pagedData1.setTotalElements(1);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<>(
            BASE_URL + "/search", "정상 조회", td1, ed1, status().isOk(), pr1));

    /*
     * [TC_ID : TC-158] [TC명 : 다국어 리스트 조회2] [REQ_ID : REQ_ADM_033] [화면 : 기준 정보 > 다국어
     * 관리] [기능 : 다국어 리스트 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetailSearchForm<>(
            "N/A", "필수값 누락", td1, ed1, status().isOk(), pr1));

    /*
     * /*
     * [TC_ID : TC-159] [TC명 : 다국어 리스트 조회3] [REQ_ID : REQ_ADM_033] [화면 : 기준 정보 > 다국어
     * 관리] [기능 : 다국어 리스트 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetailSearchForm<>(
            "N/A", "필수값 누락", td1, ed1, status().isOk(), pr1));
    /*
     * [TC_ID : TC-160] [TC명 : 다국어 리스트 조회4] [REQ_ID : REQ_ADM_033] [화면 : 기준 정보 > 다국어
     * 관리] [기능 : 다국어 리스트 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetailSearchForm<>(
            "N/A", "필수값 누락", td1, ed1, status().isOk(), pr1));
    /*
     * [TC_ID : TC-161] [TC명 : 다국어 리스트 조회5] [REQ_ID : REQ_ADM_033] [화면 : 기준 정보 > 다국어
     * 관리] [기능 : 다국어 리스트 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetailSearchForm<>(BASE_URL + "search//", "잘못된 형식 입력",
            null,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError(), pr1));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "다국어 목록조회 : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            Class<?> type = parameterContext.getParameter().getType();
            // TestCaseDetailSearchForm만 지원
            return TestCaseDetailSearchForm.class.isAssignableFrom(type);
          }

          @Override
          public Object resolveParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            return testCase;
          }
        });
      }
    });
  }
}
