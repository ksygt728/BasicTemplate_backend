package com.basic.app.featureTest.testcases.scheduler;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.CompanyResDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class SchedulerTestCasesForInesrt implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/scheduler";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        CompanyResDto testDataCompanyAll = new CompanyResDto("C100_TEST", "Test Company");

        /*
         * [TC_ID : TC-382] [TC명 : 스케줄러 추가1] [REQ_ID : REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
         */
        String testName_order1 = "정상 등록 |";
        ScheMReqDto testData_order1 = new ScheMReqDto(
                "sche-888_test",
                "리포트 생성",
                "매월 1일 새벽 2시 리포트 생성",
                "REPORT_GROUP",
                "com.basic.app.job.ReportJob",
                "generateReport",
                "reportTrigger",
                "0 0 2 1 * ?",
                "N");
        ResponseApi<?> expected_order1 = ResponseApi.success(Map.of("data", new ScheMResDto(
                "sche-888_test",
                "리포트 생성",
                "매월 1일 새벽 2시 리포트 생성",
                "REPORT_GROUP",
                "com.basic.app.job.ReportJob",
                "generateReport",
                "reportTrigger",
                "0 0 2 1 * ?",
                null,
                null,
                "N")));
        String url_order1 = BASE_URL;
        ResultMatcher status_order1 = status().isOk();

        /*
         * [TC_ID : TC-383] [TC명 : 스케줄러 추가2] [REQ_ID : REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 추가] [테스트항목 : 필수값 누락] [테스트 상세 : ]
         */
        String testName_order2 = "필수값 누락 | scheId 누락";
        ScheMReqDto testData_order2 = new ScheMReqDto(null,
                "로그 정리",
                "매주 일요일 새벽 3시 로그 삭제",
                "CLEANUP_GROUP",
                "com.basic.app.job.LogCleanupJob",
                "cleanLogs",
                "cleanupTrigger",
                "0 0 3 ? * SUN",
                "N");
        ResponseApi<?> expected_order2 = ResponseApi.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "스케줄아이디는 필수입니다.");
        String url_order2 = BASE_URL;
        ResultMatcher status_order2 = status().is4xxClientError();

        /*
         * [TC_ID : TC-384] [TC명 : 스케줄러 추가3] [REQ_ID : REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 추가] [테스트항목 : 존재하는 ID 등록 시도] [테스트 상세 : ]
         */
        String testName_order3 = "존재하는 ID 등록 시도 | 중복 scheId";
        ScheMReqDto testData_order3 = new ScheMReqDto("sche-004_test",
                "로그 정리",
                "매주 일요일 새벽 3시 로그 삭제",
                "CLEANUP_GROUP",
                "com.basic.app.job.LogCleanupJob",
                "cleanLogs",
                "cleanupTrigger",
                "0 0 3 ? * SUN",
                "N");
        ResponseApi<?> expected_order3 = ResponseApi.fail(ErrorCode.OBJECT_IS_EXISTED);
        String url_order3 = BASE_URL;
        ResultMatcher status_order3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-385] [TC명 : 스케줄러 추가4] [REQ_ID : REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스는 미존재

        /*
         * [TC_ID : TC-386] [TC명 : 스케줄러 추가5] [REQ_ID : REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        String testName_order5 = "잘못된 형식 입력";
        ScheMResDto testData_order5 = new ScheMResDto("sche-004_test",
                "로그 정리",
                "매주 일요일 새벽 3시 로그 삭제",
                "CLEANUP_GROUP",
                "com.basic.app.job.LogCleanupJob",
                "cleanLogs",
                "cleanupTrigger",
                "0 0 3 ? * SUN",
                LocalDateTime.of(2025, 8, 3, 3, 0, 0, 0),
                LocalDateTime.of(2025, 8, 10, 3, 0, 0, 0),
                "N");
        ResponseApi<?> expected_order5 = ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND);
        String url_order5 = BASE_URL + "/";
        ResultMatcher status_order5 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url_order1, testName_order1, testData_order1, expected_order1, status_order1),
                new TestCaseDetail<>(url_order2, testName_order2, testData_order2, expected_order2, status_order2),
                new TestCaseDetail<>(url_order3, testName_order3, testData_order3, expected_order3, status_order3),
                new TestCaseDetail<>(url_order5, testName_order5, testData_order5, expected_order5, status_order5));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "스케줄러 추가 : " + (testCase.getTestName());
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