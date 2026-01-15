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
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.entity.Company;
import com.basic.app.entity.Department;
import com.basic.app.util.TestCaseDetailSearchForm;

/**
 * @파일명 : RoleUserTestCasesForUserSearch.java
 * @설명 : 사용자별 권한 사용자 리스트 조회 테스트 케이스 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
public class RoleUserTestCasesForUserSearch implements
    TestTemplateInvocationContextProvider {
  private final String BASE_URL = "/admin/role/user";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

    List<TestCaseDetailSearchForm<UserReqDto, RoleUserResDto>> testCases = new ArrayList<TestCaseDetailSearchForm<UserReqDto, RoleUserResDto>>();
    /*
     * [TC_ID : TC-048] [TC명 : 사용자별 권한 사용자 리스트 조회1] [REQ_ID : REQ_ADM_048] [화면 : 권한
     * 관리 > 사용자별 권한]
     * [기능 : 사용자 리스트 조회] [테스트항목 : [N건] 리스트 페이징 조회] [테스트 상세 : ]
     */

    UserReqDto td1 = UserReqDto.builder()
        .userId("[JUnit]testuser001")
        .name("[JUnit]테스트사용자1")
        .build();

    PageRequest pr1 = PageRequest.of(0, 2000, Sort.by("userId").ascending());

    // Response
    List<UserResDto> data1 = List.of(
        UserResDto.builder()
            .userId("[JUnit]testuser001")
            .name("[JUnit]테스트사용자1")
            .phoneNum("010-1111-1111")
            .email("testuser001@test.com")
            .role("ROLE_GUEST")
            .userType("CBMS")
            .gender("M")
            .department(DepartmentResDto.builder()
                .deptCode("20000000")
                .deptNm("CBMS")
                .deptLv(0)
                .upperDeptCode("ROOT")
                .useYn("Y")
                .company(
                    CompanyResDto.builder()
                        .companyCode("C100")
                        .companyName("CBMS회사")
                        .build())
                .build())
            .build());

    PageResponse<UserResDto> pagedData1 = new PageResponse<>(new PageImpl<>(
        data1, pr1, 1)); // totalElements=3
    pagedData1.setTotalElements(1);
    pagedData1.setTotalPages(1);
    pagedData1.setFirst(true);
    pagedData1.setLast(true);
    ResponseApi<?> ed1 = ResponseApi.success(Map.of("data", pagedData1));

    testCases.add(
        new TestCaseDetailSearchForm<UserReqDto, RoleUserResDto>(
            BASE_URL, "정상조회(N건)", td1, ed1, status().isOk(), pr1)

    );

    // 각 케이스에 대한 InvocationContext 생성
    return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return "사용자별 권한 사용자 리스트 조회 : [" + invocationIndex + "] " +
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