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

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : MulLangTestCasesForSearch.java
 * @설명 : 다국어 정보 상세조회 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.09.10
 * @변경이력 :
 *       2025.09.10 김승연 최초 생성
 */
public class MulLangTestCasesForSearch implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/lang";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-162] [TC명 : 다국어 리스트 언어별 조회1] [REQ_ID : REQ_ADM_034] [화면 : 기준 정보 >
     * 다국어 관리] [기능 : 다국어 리스트 언어별 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    String langGubun = "msg";
    String langCd = "[JUnit]TEST001";

    List ed1 = List.of(
        MulLangResDto.builder()
            .langCd(langCd)
            .langType("en")
            .langGubun(langGubun)
            .langNm("[JUnit]Test Message 1")
            .useYn("Y")
            .build(),
        MulLangResDto.builder()
            .langCd(langCd)
            .langType("ko")
            .langGubun(langGubun)
            .langNm("[JUnit]테스트 메시지 1")
            .useYn("Y")
            .build()

    );

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/" + langGubun + "/" + langCd, "정상 조회",
            Map.of("langGubun", langGubun, "langCd", langCd),
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-163] [TC명 : 다국어 리스트 언어별 조회2] [REQ_ID : REQ_ADM_034] [화면 : 기준 정보 >
     * 다국어 관리] [기능 : 다국어 리스트 언어별 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */
    testCases.add(new TestCaseDetail<>("N/A", "필수값 누락", null, null, status().isOk()));

    /*
     * [TC_ID : TC-164] [TC명 : 다국어 리스트 언어별 조회3] [REQ_ID : REQ_ADM_034] [화면 : 기준 정보 >
     * 다국어 관리] [기능 : 다국어 리스트 언어별 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "존재하지 않는 ID 조회", null, null, status().isOk()));

    /*
     * [TC_ID : TC-165] [TC명 : 다국어 리스트 언어별 조회4] [REQ_ID : REQ_ADM_034] [화면 : 기준 정보 >
     * 다국어 관리] [기능 : 다국어 리스트 언어별 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));
    /*
     * [TC_ID : TC-166] [TC명 : 다국어 리스트 언어별 조회5] [REQ_ID : REQ_ADM_034] [화면 : 기준 정보 >
     * 다국어 관리] [기능 : 다국어 리스트 언어별 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "//", "잘못된 형식 입력",
            null,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "다국어 조회 : " + (testCase.getTestName());
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            Class<?> type = parameterContext.getParameter().getType();
            // TestCaseDetail만 지원
            return TestCaseDetail.class.isAssignableFrom(type);
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
