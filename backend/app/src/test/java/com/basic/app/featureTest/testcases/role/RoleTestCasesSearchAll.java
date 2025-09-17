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
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.util.TestCaseDetailSearchForm;

/**
 * @파일명 : RoleFormatTestCasesSearchAll.java
 * @설명 : 권한 정보 전체 조회 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class RoleTestCasesSearchAll implements
    TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/role";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<RoleReqDto, RoleResDto>> testCases = new ArrayList<TestCaseDetailSearchForm<RoleReqDto, RoleResDto>>();
    /*
     * [TC_ID : TC-001] [TC명 : 권한 정보 조회1] [REQ_ID : REQ_ADM_042] [화면 : 권한 관리 > 권한
     * 관리(Role)]
     * [기능 : 권한 리스트 조회] [테스트항목 : [N건] 리스트 페이징 조회] [테스트 상세 : ]
     */

    RoleReqDto td1 = RoleReqDto.builder()
        .roleCd("[JUnit]ROLE_ADMIN")
        .roleName("[JUnit]시스템관리자")
        .roleDesc("[JUnit]시스템 전체 관리 권한")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("roleCd").ascending());

    // Response
    List<RoleResDto> data1 = List.of(
        RoleResDto.builder()
            .roleCd("[JUnit]ROLE_ADMIN")
            .roleName("[JUnit]시스템관리자")
            .roleDesc("[JUnit]시스템 전체 관리 권한")
            .build());

    PageResponse<RoleResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 1)); // totalElements=1
    pagedData1.setTotalElements(1);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<RoleReqDto, RoleResDto>(
            BASE_URL + "/search", "정상조회(N건)", td1, ed1, status().isOk(), pr1)

    );

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "권한 조회 : [" + invocationIndex + "] " +
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