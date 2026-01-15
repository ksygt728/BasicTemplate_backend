package com.basic.app.featureTest.testcases.department;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class DepartmentTestCasesForSearch implements TestTemplateInvocationContextProvider {

    private final String BASE_URL = "/admin/department";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        CompanyResDto testDataCompanyAll = new CompanyResDto("C100_TEST", "Test Company");

        /*
         * [TC_ID : TC-017] [TC명 : 부서 정보 조회1] [REQ_ID : REQ_ADM_005] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : 일반부서조회 | ROOT 부서조회]
         */
        String testName_order1 = "정상조회 | 일반부서조회";
        DepartmentResDto testData_order1 = new DepartmentResDto("21000000_TEST", "경영지원본부", "20000000_TEST", 1,
                testDataCompanyAll, "Y");
        ResponseApi<?> expected_order1 = ResponseApi.success(Map.of("data", testData_order1));
        String url_order1 = BASE_URL + "/" + testData_order1.getDeptCode();
        ResultMatcher status_order1 = status().isOk();

        String testName_order2 = "정상조회 | ROOT 부서조회";
        DepartmentResDto testData_order2 = new DepartmentResDto("20000000_TEST", "CBMS", "ROOT", 0, testDataCompanyAll,
                "Y");
        ResponseApi<?> expected_order2 = ResponseApi.success(Map.of("data", testData_order2));
        String url_order2 = BASE_URL + "/" + testData_order2.getDeptCode();
        ResultMatcher status_order2 = status().isOk();

        /*
         * [TC_ID : TC-018] [TC명 : 부서 정보 조회2] [REQ_ID : REQ_ADM_005] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-019] [TC명 : 부서 정보 조회3] [REQ_ID : REQ_ADM_005] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
         */
        String testName_order3 = "존재하지 않는 ID 조회 | ";
        DepartmentResDto testData_order3 = new DepartmentResDto("20000000113234", "CBMS", "ROOT", 0, testDataCompanyAll,
                "Y");
        ResponseApi<?> expected_order3 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url_order3 = BASE_URL + "/" + testData_order3.getDeptCode();
        ResultMatcher status_order3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-020] [TC명 : 부서 정보 조회4] [REQ_ID : REQ_ADM_005] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-021] [TC명 : 부서 정보 조회5] [REQ_ID : REQ_ADM_005] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        String testName_order4 = "잘못된 형식 입력";
        DepartmentResDto testData_order4 = null; // 잘못된 형식 입력을 위한 DTO는 null로 설정
        ResponseApi<?> expected_order4 = ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND);
        String url_order4 = BASE_URL + "/";
        ResultMatcher status_order4 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetailForSearch 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(

                new TestCaseDetail<>(url_order1, testName_order1, testData_order1, expected_order1, status_order1),
                new TestCaseDetail<>(url_order2, testName_order2, testData_order2, expected_order2, status_order2),
                new TestCaseDetail<>(url_order3, testName_order3, testData_order3, expected_order3, status_order3),
                new TestCaseDetail<>(url_order4, testName_order4, testData_order4, expected_order4, status_order4));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "부서 조회 : " + (testCase.getTestName());

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