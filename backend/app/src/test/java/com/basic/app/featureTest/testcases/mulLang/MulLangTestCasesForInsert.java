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
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : MulLangTestCasesForInsert.java
 * @설명 : 다국어 정보 추가 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.09.10
 * @변경이력 :
 *       2025.09.10 김승연 최초 생성
 */
public class MulLangTestCasesForInsert implements TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/lang";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-167] [TC명 : 다국어 추가1] [REQ_ID : REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
     */

    MulLangReqDto td1 = MulLangReqDto.builder()
        .langCd("[JUnit]NEW001")
        .langType("ko")
        .langGubun("msg")
        .langNm("새로운 테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 추가", td1,
            ResponseApi.success(Map.of("data", td1)), status().isOk()));

    /*
     * [TC_ID : TC-168] [TC명 : 다국어 추가2] [REQ_ID : REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */

    MulLangReqDto td2 = MulLangReqDto.builder()
        // langCd 누락
        .langType("ko")
        .langGubun("msg")
        .langNm("테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langCd", td2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어코드는 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_2 = MulLangReqDto.builder()
        .langCd("[JUnit]NEW002_2")
        // langType 누락
        .langGubun("msg")
        .langNm("테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langType", td2_2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어유형은 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_3 = MulLangReqDto.builder()
        .langCd("[JUnit]NEW002_3")
        .langType("ko")
        .langGubun("msg")
        // langNm 누락
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langNm", td2_3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어명은 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_4 = MulLangReqDto.builder()
        .langCd("[JUnit]NEW002_4")
        .langType("ko")
        // langGubun 누락
        .langNm("테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - langGubun", td2_4,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "언어구분은 필수입니다."), status().is4xxClientError()));

    MulLangReqDto td2_5 = MulLangReqDto.builder()
        .langCd("[JUnit]NEW002_5")
        .langType("ko")
        .langGubun("msg")
        .langNm("테스트 메시지")
        // useYn 누락
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - useYn", td2_5,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용여부는 필수입니다."), status().is4xxClientError()));

    /*
     * [TC_ID : TC-169] [TC명 : 다국어 추가3] [REQ_ID : REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
     */

    MulLangReqDto td3 = MulLangReqDto.builder()
        .langCd("[JUnit]TEST001") // 이미 존재하는 키
        .langType("ko")
        .langGubun("msg")
        .langNm("중복 테스트 메시지")
        .useYn("Y")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하는 ID 등록 시도", td3,
            ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED), status().is4xxClientError()));

    /*
     * [TC_ID : TC-170] [TC명 : 다국어 추가4] [REQ_ID : REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */

    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-171] [TC명 : 다국어 추가5] [REQ_ID : REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리]
     * [기능 : 다국어 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    MulLangReqDto td4 = MulLangReqDto.builder()
        .langCd("[JUnit]NEW004")
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
        return "[" + invocationIndex + "] " + "다국어 추가 : " + (testCase.getTestName());
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
