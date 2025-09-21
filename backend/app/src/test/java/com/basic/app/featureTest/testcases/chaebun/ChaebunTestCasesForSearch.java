package com.basic.app.featureTest.testcases.chaebun;

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
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class ChaebunTestCasesForSearch implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/chaebun";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-257] [TC명 : 채번 리스트 조회1] [REQ_ID : REQ_ADM_053] [화면 : 시스템 관리 >
     * 채번관리] [기능 : 채번 리스트 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    String td1 = "[JUnit]SEQ001";
    ChaebunResDto ed1 = ChaebunResDto.builder()
        .seqId("[JUnit]SEQ001")
        .seqName("[JUnit]회원번호채번")
        .pattern("{PREFIX}_{DATEFORMAT}_{VALUE}")
        .prefix("[JUnit]MEMBER")
        .currentValue(0)
        .step(1)
        .length(4)
        .dateformat("yyyyMMdd")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]SEQ001", "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-258] [TC명 : 채번 리스트 조회2] [REQ_ID : REQ_ADM_053] [화면 : 시스템 관리 >
     * 채번관리] [기능 : 채번 리스트 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */
    // 해당 케이스 미존재 (PathVariable이므로 필수값 누락 불가)
    testCases.add(new TestCaseDetail<>("N/A", "필수값 누락", null, null, status().isOk()));

    /*
     * [TC_ID : TC-259] [TC명 : 채번 리스트 조회3] [REQ_ID : REQ_ADM_053] [화면 : 시스템 관리 >
     * 채번관리] [기능 : 채번 리스트 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]SEQ001_NOT_EXIST", "존재하지 않는 ID 조회", "SEQ001_NOT_EXIST",
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

    /*
     * [TC_ID : TC-260] [TC명 : 채번 리스트 조회4] [REQ_ID : REQ_ADM_053] [화면 : 시스템 관리 >
     * 채번관리] [기능 : 채번 리스트 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-261] [TC명 : 채번 리스트 조회5] [REQ_ID : REQ_ADM_053] [화면 : 시스템 관리 >
     * 채번관리] [기능 : 채번 리스트 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력",
            "", ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "채번 조회(단건) : " + (testCase.getTestName());
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