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
import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : MulLangTestCasesForUpdate.java
 * @설명 : 다국어 정보 수정 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.09.10
 * @변경이력 :
 *       2025.09.10 김승연 최초 생성
 */
public class MulLangTestCasesForUpdate implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/lang";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();
    /*
     * [TC_ID : TC-172] [TC명 : 다국어 수정1] [REQ_ID : REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
     */

    MulLangReqDto td1 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        .langGubun("msg")
        .langNm("수정된 테스트 메시지")
        .useYn("Y")
        .build();

    MulLangResDto ed1 = MulLangResDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        .langGubun("msg")
        .langNm("수정된 테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));
    /*
     * [TC_ID : TC-173] [TC명 : 다국어 수정2] [REQ_ID : REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    MulLangReqDto td2 = MulLangReqDto.builder()
        // langCd 누락
        .langType("ko")
        .langGubun("msg")
        .langNm("수정된 테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langCd", td2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어코드는 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_2 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        // langType 누락
        .langGubun("msg")
        .langNm("수정된 테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langType", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어유형은 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_3 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        .langGubun("msg")
        // langNm 누락
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langNm", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어명은 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_4 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        // langGubun 누락
        .langNm("수정된 테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langGubun", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어구분은 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_5 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        .langGubun("msg")
        .langNm("수정된 테스트 메시지")
        // useYn 누락
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - useYn", td2_5,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."), status().is4xxClientError()));
    /*
     * [TC_ID : TC-174] [TC명 : 다국어 수정3] [REQ_ID : REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
     */

    MulLangReqDto td3 = MulLangReqDto.builder()
        .langCd("[JUnit]NOT_EXIST")
        .langType("ko")
        .langGubun("msg")
        .langNm("존재하지 않는 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정", td3,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-175] [TC명 : 다국어 수정4] [REQ_ID : REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-176] [TC명 : 다국어 수정5] [REQ_ID : REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    MulLangReqDto td4 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001")
        .langType("ko")
        .langGubun("msg")
        .langNm("") // 빈 문자열
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td4,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "다국어 수정 : " + (testCase.getTestName());
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
