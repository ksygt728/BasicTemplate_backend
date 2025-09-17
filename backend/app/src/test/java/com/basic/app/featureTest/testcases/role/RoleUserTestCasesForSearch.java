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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.basic.app.api.PageResponse;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetailSearchForm;

/**
 * @파일명 : RoleUserTestCasesForSearch.java
 * @설명 : 사용자별 권한 조회 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class RoleUserTestCasesForSearch implements
    TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/role/role-user";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<String, RoleUserResDto>> testCases = new ArrayList<TestCaseDetailSearchForm<String, RoleUserResDto>>();
    /*
     * [TC_ID : TC-049] [TC명 : 사용자별 권한 조회1] [REQ_ID : REQ_ADM_049] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 조회] [테스트항목 : [N건] 리스트 페이징 조회] [테스트 상세 : ]
     */

    String td1 = "[JUnit]testuser001"; // 사용자 ID

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("roleUserId.roleCd").ascending());

    // Response - testuser001은 ADMIN 권한 가지고 있음
    List<RoleUserResDto> data1 = List.of(
        RoleUserResDto.builder()
            .userId("[JUnit]testuser001")
            .useYn("Y")
            .role(RoleResDto.builder()
                .roleCd("[JUnit]ROLE_ADMIN")
                .roleName("[JUnit]시스템관리자")
                .roleDesc("[JUnit]시스템 전체 관리 권한")
                .build())
            .build());

    PageResponse<RoleUserResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 1)); // totalElements=1
    pagedData1.setTotalElements(1);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<String, RoleUserResDto>(
            BASE_URL + "/[JUnit]testuser001", "정상조회", td1, ed1, status().isOk(), pr1)

    );

    /*
     * [TC_ID : TC-050] [TC명 : 사용자별 권한 조회2] [REQ_ID : REQ_ADM_049] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 조회] [테스트항목 : 존재하지 않는 사용자] [테스트 상세 : ]
     */
    // 테스트 케이스 미존재
    String td2 = "[JUnit]user_not_exist";
    PageRequest pr2 = PageRequest.of(0, 2000, Sort.by("roleUserId.roleCd").ascending());

    testCases.add(
        new TestCaseDetailSearchForm<String, RoleUserResDto>(
            "N/A", "존재하지 않는 사용자", td2,
            ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError(), pr2)

    );

    /*
     * [TC_ID : TC-051] [TC명 : 사용자별 권한 조회3] [REQ_ID : REQ_ADM_049] [화면 : 권한 관리 >
     * 사용자별 권한]
     * [기능 : 사용자별 권한 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
     */
    String td3 = "";
    PageRequest pr3 = PageRequest.of(0, 2000, Sort.by("roleUserId.roleCd").ascending());

    testCases.add(
        new TestCaseDetailSearchForm<String, RoleUserResDto>(
            BASE_URL + "/", "잘못된 형식 입력", td3,
            ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError(), pr3)

    );

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "사용자별 권한 조회 : [" + invocationIndex + "] " +
            (testCase.getTestName());

      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return List.of(new ParameterResolver() {
          @Override
          public boolean supportsParameter(ParameterContext parameterContext,
              ExtensionContext extensionContext) {
            Class<?> type = parameterContext.getParameter().getType();
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