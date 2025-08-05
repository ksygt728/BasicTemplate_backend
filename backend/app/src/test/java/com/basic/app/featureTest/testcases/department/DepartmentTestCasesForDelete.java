package com.basic.app.featureTest.testcases.department;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.DepartmentReqDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class DepartmentTestCasesForDelete implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/department";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        CompanyResDto testDataCompanyAll = new CompanyResDto("C100_TEST", "Test Company");

        /*
         * [TC_ID : TC-026] [TC명 : 부서 정보 추가4] [REQ_ID : REQ_ADM_006] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : 삭제불가]
         */

        String testName_order1 = "비즈니스로직 케이스 | 삭제불가";
        DepartmentReqDto testData_order1 = new DepartmentReqDto("20000000_TEST", "경영지원본부", "20000000_TEST", 1,
                "C100_TEST", "Y");
        String url_order1 = BASE_URL + "/" + testData_order1.getDeptCode();
        ApiResponse<?> expected_order1 = ApiResponse.fail(ErrorCode.DEPARTMENT_NOT_WRITE);
        ResultMatcher status_order1 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url_order1, testName_order1, testData_order1, expected_order1, status_order1));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "부서 삭제 : " + (testCase.getTestName());
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