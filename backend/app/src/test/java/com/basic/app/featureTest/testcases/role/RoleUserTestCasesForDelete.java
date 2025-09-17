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
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

/**
 * @파일명 : RoleUserTestCasesForDelete.java
 * @설명 : 사용자별 권한 삭제 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class RoleUserTestCasesForDelete implements TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/role/role-user";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
    List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

    /*
     * [TC_ID : TC-052] [TC명 : 사용자별 권한 삭제1] [REQ_ID : REQ_ADM_052] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
     */
    List<RoleUserReqDto> td1 = List.of(
        RoleUserReqDto.builder()
            .roleCd("[JUnit]ROLE_USER")
            .userId("[JUnit]testuser002")
            .useYn("Y")
            .build(),
        RoleUserReqDto.builder()
            .roleCd("[JUnit]ROLE_MANAGER")
            .userId("[JUnit]testuser003")
            .useYn("Y")
            .build());

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "정상 삭제", Map.of("roleUsers", td1),
            ResponseApi.success(Map.of("data", "success")), status().isOk()));

    /*
     * [TC_ID : TC-053] [TC명 : 사용자별 권한 삭제2] [REQ_ID : REQ_ADM_052] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
     */
    List<RoleUserReqDto> td2 = List.of(
        RoleUserReqDto.builder()
            .roleCd("[JUnit]ROLE_USER")
            .userId("[JUnit]testuser002") // 이미 삭제된 권한 관계
            .useYn("Y")
            .build());

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "이미 삭제한 항목 삭제 시도", Map.of("roleUsers", td2),
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-054] [TC명 : 사용자별 권한 삭제3] [REQ_ID : REQ_ADM_052] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 삭제] [테스트항목 : 존재하지 않는 권한-사용자 관계 삭제 시도] [테스트 상세 : ]
     */
    List<RoleUserReqDto> td3 = List.of(
        RoleUserReqDto.builder()
            .roleCd("[JUnit]ROLE_NOT_EXIST")
            .userId("[JUnit]testuser001")
            .useYn("Y")
            .build());

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "존재하지 않는 권한-사용자 관계 삭제 시도", Map.of("roleUsers", td3),
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-055] [TC명 : 사용자별 권한 삭제4] [REQ_ID : REQ_ADM_052] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 삭제] [테스트항목 : 필수값 누락] [테스트 상세 : ]
     */
    List<RoleUserReqDto> td4 = List.of(
        RoleUserReqDto.builder()
            // roleCd 누락
            .userId("[JUnit]testuser001")
            .useYn("Y")
            .build());

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - roleCd", Map.of("roleUsers", td4),
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "권한코드는 필수입니다."),
            status().is4xxClientError()));

    List<RoleUserReqDto> td5 = List.of(
        RoleUserReqDto.builder()
            .roleCd("[JUnit]ROLE_ADMIN")
            // userId 누락
            .useYn("Y")
            .build());

    testCases.add(
        new TestCaseDetail<>(BASE_URL, "필수값 누락 - userId", Map.of("roleUsers", td5),
            ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "사용자아이디는 필수입니다."),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-056] [TC명 : 사용자별 권한 삭제5] [REQ_ID : REQ_ADM_052] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
     */
    // 해당 케이스 미존재
    testCases.add(
        new TestCaseDetail<>("N/A", "비즈니스로직 케이스", null,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND),
            status().is4xxClientError()));

    /*
     * [TC_ID : TC-057] [TC명 : 사용자별 권한 삭제6] [REQ_ID : REQ_ADM_052] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    List<RoleUserReqDto> td6 = List.of(
        RoleUserReqDto.builder()
            .roleCd("[JUnit]ROLE_ADMIN")
            .userId("") // 빈 문자열
            .useYn("Y")
            .build());

    testCases.add(
        new TestCaseDetail<>(BASE_URL + "/", "잘못된 형식 입력", Map.of("roleUsers", td6),
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND),
            status().is4xxClientError()));

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "[" + invocationIndex + "] " + "사용자별 권한 삭제 : " + (testCase.getTestName());
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