package com.basic.app.featureTest.testcases.scheduler;

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
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class SchedulerTestCasesForDelete implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/scheduler";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        CompanyResDto testDataCompanyAll = new CompanyResDto("C100_TEST", "Test Company");
        /*
         * [TC_ID : TC-392] [TC명 : 스케줄러 삭제1] [REQ_ID : REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
         */
        String testName_order1 = "정상 삭제";
        String testData_order1 = "sche-003_test"; // 비활성 상태인 로그 정리 스케줄러
        ResponseApi<?> expected_order1 = ResponseApi.success(Map.of("data", "success"));
        String url_order1 = BASE_URL + "/" + testData_order1;
        ResultMatcher status_order1 = status().isOk();

        /*
         * [TC_ID : TC-393] [TC명 : 스케줄러 삭제2] [REQ_ID : REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
         */
        String testName_order2 = "이미 삭제한 항목 삭제 시도";
        String testData_order2 = "sche-005_test"; // 이미 삭제된 스케줄러 ID
        ResponseApi<?> expected_order2 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url_order2 = BASE_URL + "/" + testData_order2;
        ResultMatcher status_order2 = status().is4xxClientError();

        /*
         * [TC_ID : TC-394] [TC명 : 스케줄러 삭제3] [REQ_ID : REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
         */
        String testName_order3 = "존재하지 않는 ID 삭제 시도";
        String testData_order3 = "sche-999_nonexistent"; // 존재하지 않는 ID
        ResponseApi<?> expected_order3 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url_order3 = BASE_URL + "/" + testData_order3;
        ResultMatcher status_order3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-395] [TC명 : 스케줄러 삭제4] [REQ_ID : REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */

        /*
         * [TC_ID : TC-396] [TC명 : 스케줄러 삭제5] [REQ_ID : REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        String testName_order5 = "잘못된 형식 입력";
        String testData_order5 = null; // 빈 ID
        ResponseApi<?> expected_order5 = ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND);
        String url_order5 = BASE_URL + "/";
        ResultMatcher status_order5 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url_order1, testName_order1, testData_order1, expected_order1, status_order1),
                new TestCaseDetail<>(url_order2, testName_order2, testData_order2, expected_order2, status_order2),
                new TestCaseDetail<>(url_order3, testName_order3, testData_order3, expected_order3, status_order3),
                // new TestCaseDetail<>(url_order4, testName_order4, testData_order4,
                // expected_order4, status_order4),
                new TestCaseDetail<>(url_order5, testName_order5, testData_order5, expected_order5, status_order5));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "스케줄러 삭제 : " + (testCase.getTestName());
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