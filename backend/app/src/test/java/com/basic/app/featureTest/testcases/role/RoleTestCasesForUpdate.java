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
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : RoleTestCasesForUpdate.java
 * @설명 : 권한 정보 수정 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class RoleTestCasesForUpdate implements TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/role";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-007] [TC명 : 권한 정보 수정1] [REQ_ID : REQ_ADM_046] [화면 : 권한 관리 > 권한
     * 관리(Role)]
     * [기능 : 권한 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
     */

    RoleReqDto td1 = RoleReqDto.builder()
        .roleCd("[JUnit]ROLE_ADMIN")
        .roleName("[JUnit]시스템관리자")
        .roleDesc("[JUnit]시스템 전체 관리 권한 수정됨") // 설명 수정
        .build();

    RoleResDto ed1 = RoleResDto.builder()
        .roleCd("[JUnit]ROLE_ADMIN")
        .roleName("[JUnit]시스템관리자")
        .roleDesc("[JUnit]시스템 전체 관리 권한 수정됨")
        .build();

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 수정", td1,
            ResponseApi.success(Map.of("data", ed1)), status().isOk()));
    /*
     * [TC_ID : TC-008] [TC명 : 권한 정보 수정2] [REQ_ID : REQ_ADM_046] [화면 : 권한 관리 > 권한
     * 관리(Role)]
     * [기능 : 권한 수정] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */
    RoleReqDto td2 = RoleReqDto.builder()
        .roleName("[JUnit]시스템관리자")
        .roleDesc("[JUnit]시스템 전체 관리 권한")
        .build();
    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 : 권한코드 누락", td2,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "권한코드는 필수입니다."),
            status().is4xxClientError()));

    RoleReqDto td3 = RoleReqDto.builder()
        .roleCd("[JUnit]ROLE_ADMIN")
        .roleDesc("[JUnit]시스템 전체 관리 권한")
        .build();
    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 : 권한명 누락", td3,
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "권한명은 필수입니다."),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-009] [TC명 : 권한 정보 수정3] [REQ_ID : REQ_ADM_046] [화면 : 권한 관리 > 권한
     * 관리(Role)]
     * [기능 : 권한 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
     */
    RoleReqDto td4 = RoleReqDto.builder()
        .roleCd("[JUnit]ROLE_NOT_EXIST")
        .roleName("[JUnit]존재하지않는권한")
        .roleDesc("[JUnit]존재하지 않는 권한 설명")
        .build();
    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하지 않는 ID 수정 시도", td4,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-010] [TC명 : 권한 정보 수정4] [REQ_ID : REQ_ADM_046] [화면 : 권한 관리 > 권한
     * 관리(Role)]
     * [기능 : 권한 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null, null, status().isOk()));

    /*
     * [TC_ID : TC-011] [TC명 : 권한 정보 수정5] [REQ_ID : REQ_ADM_046] [화면 : 권한 관리 > 권한
     * 관리(Role)]
     * [기능 : 권한 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    RoleReqDto td5 = RoleReqDto.builder()
        .roleCd("[JUnit]ROLE_ADMIN")
        .roleName("") // 빈 문자열
        .roleDesc("[JUnit]시스템 전체 관리 권한")
        .build();
    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", td5,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "권한 수정 : " + (testCase.getTestName());
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