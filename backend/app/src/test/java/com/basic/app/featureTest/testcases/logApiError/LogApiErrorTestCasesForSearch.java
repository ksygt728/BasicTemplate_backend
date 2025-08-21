package com.basic.app.featureTest.testcases.logApiError;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.dto.requestDto.LogApiReqDto;
import com.basic.app.dto.requestDto.LogErrorReqDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TimeKeeper;

public class LogApiErrorTestCasesForSearch implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/log";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Autowired
    TimeKeeper timeKeeper;

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-312] [TC명 : 사용자 접속로그 조회1] [REQ_ID : REQ_ADM_064] [화면 : 시스템 관리 >
         * 사용자 접속 로그] [기능 : 사용자 접속로그 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */

        String testName1 = "정상조회";
        LogApiReqDto testData1 = new LogApiReqDto(
                "9de0b889-dfad-47ce-af60-b7bb7f5c7480",
                "testUser",
                "2025-07-23 10:15:30.000",
                "2025-07-23 10:15:31.000",
                "192.168.0.1",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
                "/api/test",
                "GET",
                "{\"param\":\"value\"}",
                "{\"result\":\"success\"}",
                "200",
                1000L);
        ResponseApi<?> expected1 = ResponseApi.success(Map.of("data", testData1));
        String url1 = BASE_URL + "/api-log/" + testData1.getLogId();
        ResultMatcher status1 = status().isOk();

        /*
         * [TC_ID : TC-313] [TC명 : 사용자 접속로그 조회2] [REQ_ID : REQ_ADM_064] [화면 : 시스템 관리 >
         * 사용자 접속 로그] [기능 : 사용자 접속로그 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-314] [TC명 : 사용자 접속로그 조회3] [REQ_ID : REQ_ADM_064] [화면 : 시스템 관리 >
         * 사용자 접속 로그] [기능 : 사용자 접속로그 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
         */

        String testName2 = "존재하지 않는 ID 조회";
        LogApiReqDto testData2 = new LogApiReqDto(
                "9de0b889-dfad-47ce-af60-b7bb11111111", // 존재하지 않는 ID
                "testUser",
                "2025-07-23 10:15:30.000",
                "2025-07-23 10:15:31.000",
                "192.168.0.1",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
                "/api/test",
                "GET",
                "{\"param\":\"value\"}",
                "{\"result\":\"success\"}",
                "200",
                1000L);
        ResponseApi<?> expected2 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url2 = BASE_URL + "/api-log/" + testData2.getLogId();
        ResultMatcher status2 = status().is4xxClientError();

        /*
         * [TC_ID : TC-315] [TC명 : 사용자 접속로그 조회4] [REQ_ID : REQ_ADM_064] [화면 : 시스템 관리 >
         * 사용자 접속 로그] [기능 : 사용자 접속로그 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-316] [TC명 : 사용자 접속로그 조회5] [REQ_ID : REQ_ADM_064] [화면 : 시스템 관리 >
         * 사용자 접속 로그] [기능 : 사용자 접속로그 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        String testName3 = "잘못된 형식 입력";
        String testData3 = null; // 잘못된 형식 입력을 위한 DTO는 null로 설정
        ResponseApi<?> expected3 = ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND);
        String url3 = BASE_URL + "/";
        ResultMatcher status3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-317] [TC명 : 에러 리스트 조회1] [REQ_ID : REQ_ADM_065] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 리스트 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         * [TC_ID : TC-322] [TC명 : 에러 상세정보 조회1] [REQ_ID : REQ_ADM_066] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 상세정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */

        String testName4 = "정상조회";
        LogErrorReqDto testData4 = new LogErrorReqDto(
                "9de0b889-dfad-47ce-af60-b7bb7f5c7411", // errId
                "testUser", // userId
                "192.168.0.1", // ipAddr
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)", // userAgent
                "/api/test-error", // requestUri
                "POST", // httpMethod
                "NullPointerException 발생", // errMsg
                "java.lang.NullPointerException\n\tat com.basic.app..." // errStack
        );
        ResponseApi<?> expected4 = ResponseApi.success(Map.of("data", testData4));
        String url4 = BASE_URL + "/error-log/" + testData4.getErrId();
        ResultMatcher status4 = status().isOk();

        /*
         * [TC_ID : TC-318] [TC명 : 에러 리스트 조회2] [REQ_ID : REQ_ADM_065] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 리스트 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
         * [TC_ID : TC-323] [TC명 : 에러 상세정보 조회2] [REQ_ID : REQ_ADM_066] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 상세정보 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-319] [TC명 : 에러 리스트 조회3] [REQ_ID : REQ_ADM_065] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 리스트 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
         * [TC_ID : TC-324] [TC명 : 에러 상세정보 조회3] [REQ_ID : REQ_ADM_066] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 상세정보 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
         */
        String testName5 = "존재하지 않는 ID 조회";
        LogErrorReqDto testData5 = new LogErrorReqDto(
                "9de0b889-dfad-47ce-af60-b7bb22222222", // errId
                "testUser", // userId
                "192.168.0.1", // ipAddr
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)", // userAgent
                "/api/test-error", // requestUri
                "POST", // httpMethod
                "NullPointerException 발생", // errMsg
                "java.lang.NullPointerException\n\tat com.basic.app..." // errStack
        );
        ResponseApi<?> expected5 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url5 = BASE_URL + "/error-log/" + testData5.getErrId();
        ResultMatcher status5 = status().is4xxClientError();

        /*
         * [TC_ID : TC-320] [TC명 : 에러 리스트 조회4] [REQ_ID : REQ_ADM_065] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 리스트 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
         * [TC_ID : TC-325] [TC명 : 에러 상세정보 조회4] [REQ_ID : REQ_ADM_066] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 상세정보 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-321] [TC명 : 에러 리스트 조회5] [REQ_ID : REQ_ADM_065] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 리스트 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         * [TC_ID : TC-326] [TC명 : 에러 상세정보 조회5] [REQ_ID : REQ_ADM_066] [화면 : 시스템 관리 >
         * Error 관리] [기능 : 에러 상세정보 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */

        String testName6 = "잘못된 형식 입력";
        InterfaceReqDto testData6 = null; // 잘못된 형식 입력을 위한 DTO는 null로 설정
        ResponseApi<?> expected6 = ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND);
        String url6 = BASE_URL + "/";
        ResultMatcher status6 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetailForSearch 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url1, testName1, testData1, expected1, status1),
                new TestCaseDetail<>(url2, testName2, testData2, expected2, status2),
                new TestCaseDetail<>(url3, testName3, testData3, expected3, status3),
                new TestCaseDetail<>(url4, testName4, testData4, expected4, status4),
                new TestCaseDetail<>(url5, testName5, testData5, expected5, status5),
                new TestCaseDetail<>(url6, testName6, testData6, expected6, status6));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "로그 조회 : " + (testCase.getTestName());

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