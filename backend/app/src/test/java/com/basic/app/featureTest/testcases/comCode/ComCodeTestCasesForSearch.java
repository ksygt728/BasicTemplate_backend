package com.basic.app.featureTest.testcases.comCode;

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
import com.basic.app.dto.responseDto.ComCodeDResDto;
import com.basic.app.dto.responseDto.ComCodeMResDto;
import com.basic.app.dto.responseDto.ComCodeTResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class ComCodeTestCasesForSearch implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/code";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail> testCases = new ArrayList<TestCaseDetail>();

    /*
     * [TC_ID : TC-037] [TC명 : 그뤂 코드 조회1] [REQ_ID : REQ_ADM_009] [화면 : 기준 정보 > 코드
     * 관리] [기능 : 그뤂 코드 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    ComCodeMResDto td1 = new ComCodeMResDto().builder()
        .grpCd("[JUnit]APPROVAL_STATUS")
        .grpCdType("WORKFLOW")
        .grpNm("승인 상태")
        .comCodeTs(
            List.of(
                new ComCodeTResDto("[JUnit]STATUS", "승인상태", 1,
                    List.of(
                        new ComCodeDResDto("[JUnit]PENDING", "대기중", "Y", 1),
                        new ComCodeDResDto("[JUnit]APPROVED", "승인완료", "Y", 2),
                        new ComCodeDResDto("[JUnit]REJECTED", "승인거부", "Y", 3),
                        new ComCodeDResDto("[JUnit]CANCELLED", "승인취소", "Y", 4)

                    )),
                new ComCodeTResDto("[JUnit]PRIORITY", "우선순위", 2,
                    List.of(
                        new ComCodeDResDto("[JUnit]HIGH", "높음", "Y", 1),
                        new ComCodeDResDto("[JUnit]MEDIUM", "보통", "Y", 2),
                        new ComCodeDResDto("[JUnit]LOW", "낮음", "Y", 3)

                    )),
                new ComCodeTResDto("[JUnit]LEVEL", "승인레벨", 3,
                    List.of(
                        new ComCodeDResDto("[JUnit]LEVEL1", "1차승인", "Y", 1),
                        new ComCodeDResDto("[JUnit]LEVEL2", "2차승인", "Y", 2),
                        new ComCodeDResDto("[JUnit]LEVEL3", "3차승인", "Y", 3),
                        new ComCodeDResDto("[JUnit]FINAL", "최종승인", "Y", 4))),
                new ComCodeTResDto("[JUnit]TYPE", "승인유형", 4,
                    List.of(
                        new ComCodeDResDto("[JUnit]AUTOMATIC", "자동승인", "Y", 1),
                        new ComCodeDResDto("[JUnit]MANUAL", "수동승인", "Y", 2),
                        new ComCodeDResDto("[JUnit]CONDITIONAL", "조건승인", "Y", 3)))

            ))
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/group" + "/[JUnit]APPROVAL_STATUS", "[그뤂코드] 정상 조회", td1,
            ResponseApi.success(Map.of("data", td1)), status().isOk()));

    /*
     * [TC_ID : TC-038] [TC명 : 그뤂 코드 조회2] [REQ_ID : REQ_ADM_009] [화면 : 기준 정보 > 코드
     * 관리] [기능 : 그뤂 코드 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "필수값 누락", null, null, status().isOk()));

    /*
     * [TC_ID : TC-039] [TC명 : 그뤂 코드 조회3] [REQ_ID : REQ_ADM_009] [화면 : 기준 정보 > 코드
     * 관리] [기능 : 그뤂 코드 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/group" + "/[JUnit]RISK_LEVEL", "[그뤂코드] 존재하지 않는 ID 조회 : D인걸 조회", null,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/group" + "/[JUnit]RISK_LEVEL_!!NOT!!", "[그뤂코드] 존재하지 않는 ID 조회 : 없는데이터 조회",
            null,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-040] [TC명 : 그뤂 코드 조회4] [REQ_ID : REQ_ADM_009] [화면 : 기준 정보 > 코드
     * 관리] [기능 : 그뤂 코드 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-041] [TC명 : 그뤂 코드 조회5] [REQ_ID : REQ_ADM_009] [화면 : 기준 정보 > 코드
     * 관리] [기능 : 그뤂 코드 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "[그뤂코드] 잘못된 형식 입력",
            null,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "코드 조회 : " + (testCase.getTestName());

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