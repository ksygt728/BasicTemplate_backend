package com.basic.app.featureTest.testcases.role;

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
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class RoleTestCasesForSearch implements
    TestTemplateInvocationContextProvider {

  private final String BASE_URL = "/admin/role";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail> testCases = new ArrayList<TestCaseDetail>();

    /*
     * [TC_ID : TC-002] [TC명 : 권한 정보 상세조회1] [REQ_ID : REQ_ADM_042_2] [화면 : 권한 관리 >
     * 권한 관리(Role)] [기능 : 권한 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
     */

    String td1 = "[JUnit]ROLE_ADMIN";
    RoleResDto ed1 = new RoleResDto().builder()
        .roleCd("[JUnit]ROLE_ADMIN")
        .roleName("[JUnit]시스템관리자")
        .roleDesc("[JUnit]시스템 전체 관리 권한")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]ROLE_ADMIN", "정상 조회", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));

    /*
     * [TC_ID : TC-003] [TC명 : 권한 정보 상세조회2] [REQ_ID : REQ_ADM_042_2] [화면 : 권한 관리 >
     * 권한 관리(Role)] [기능 : 권한 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "필수값 누락", null, null, status().isOk()));

    /*
     * [TC_ID : TC-004] [TC명 : 권한 정보 상세조회3] [REQ_ID : REQ_ADM_042_2] [화면 : 권한 관리 >
     * 권한 관리(Role)] [기능 : 권한 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/[JUnit]ROLE_NOT_EXIST", "존재하지 않는 ID 조회", "ROLE_NOT_EXIST",
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));
    /*
     * [TC_ID : TC-005] [TC명 : 권한 정보 상세조회4] [REQ_ID : REQ_ADM_042_2] [화면 : 권한 관리 >
     * 권한 관리(Role)] [기능 : 권한 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-006] [TC명 : 권한 정보 상세조회5] [REQ_ID : REQ_ADM_042_2] [화면 : 권한 관리 >
     * 권한 관리(Role)] [기능 : 권한 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력",
            null,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "권한 조회 : " + (testCase.getTestName());

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