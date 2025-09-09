package com.basic.app.featureTest.testcases.comCode;

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
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class ComCodeTestCasesForDelete implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/code";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        List<TestCaseDetail<?>> testCases = new ArrayList<TestCaseDetail<?>>();

        /*
         * [TC_ID : TC-052] [TC명 : 그뤂 코드 삭제1] [REQ_ID : REQ_ADM_012] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 그뤂 코드 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
         */
        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/group" + "/[JUnit]ORDER_STATUS", "[그뤂코드] 정상 삭제", null,
                        ResponseApi.success(Map.of("data", "success")), status().isOk()));

        /*
         * [TC_ID : TC-053] [TC명 : 그뤂 코드 삭제2] [REQ_ID : REQ_ADM_012] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 그뤂 코드 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/group" + "/[JUnit]RISK_LEVEL", "[그뤂코드] 이미 삭제한 항목 삭제 시도", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-054] [TC명 : 그뤂 코드 삭제3] [REQ_ID : REQ_ADM_012] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 그뤂 코드 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/group" + "/[JUnit]ASSET_STATUS_NOT_EXIST", "[그뤂코드] 존재하지 않는 ID 삭제 시도",
                        null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-055] [TC명 : 그뤂 코드 삭제4] [REQ_ID : REQ_ADM_012] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 그뤂 코드 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        testCases.add(
                new TestCaseDetail<>("N/A", "[그뤂코드] 비즈니스로직 케이스", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));
        /*
         * [TC_ID : TC-056] [TC명 : 그뤂 코드 삭제5] [REQ_ID : REQ_ADM_012] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 그뤂 코드 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/", "[그뤂코드] 잘못된 형식 입력", null,
                        ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-072] [TC명 : 속성 코드 삭제1] [REQ_ID : REQ_ADM_016] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 속성 코드 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/attribute" + "/[JUnit]ORDER_STATUS" + "/[JUnit]PROCESS",
                        "[속성코드] 정상 삭제", null,
                        ResponseApi.success(Map.of("data", "success")), status().isOk()));

        /*
         * [TC_ID : TC-073] [TC명 : 속성 코드 삭제2] [REQ_ID : REQ_ADM_016] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 속성 코드 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/attribute" + "/[JUnit]ASSET_STATUS" + "/[JUnit]CONDITION",
                        "[속성코드] 이미 삭제한 항목 삭제 시도", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-074] [TC명 : 속성 코드 삭제3] [REQ_ID : REQ_ADM_016] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 속성 코드 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/attribute" + "/[JUnit]ASSET_STATUS" + "/[JUnit]CONDITION_NOT_EXIST",
                        "[속성코드] 존재하지 않는 ID 삭제 시도", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-075] [TC명 : 속성 코드 삭제4] [REQ_ID : REQ_ADM_016] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 속성 코드 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        testCases.add(
                new TestCaseDetail<>("N/A", "[속성코드] 비즈니스로직 케이스", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-076] [TC명 : 속성 코드 삭제5] [REQ_ID : REQ_ADM_016] [화면 : 기준 정보 > 코드
         * 관리] [기능 : 속성 코드 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/", "[속성코드] 잘못된 형식 입력", null,
                        ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-092] [TC명 : 상세코드 삭제1] [REQ_ID : REQ_ADM_020] [화면 : 기준 정보 > 코드 관리]
         * [기능 : 상세코드 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(
                        BASE_URL + "/detail" + "/[JUnit]ORDER_STATUS" + "/[JUnit]PROCESS" + "/[JUnit]PENDING",

                        "[상세코드] 정상 삭제", null,
                        ResponseApi.success(Map.of("data", "success")), status().isOk()));

        /*
         * [TC_ID : TC-093] [TC명 : 상세코드 삭제2] [REQ_ID : REQ_ADM_020] [화면 : 기준 정보 > 코드 관리]
         * [기능 : 상세코드 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(
                        BASE_URL + "/detail" + "/[JUnit]ASSET_STATUS" + "/[JUnit]LOCATION" + "/[JUnit]WAREHOUSE",

                        "[상세코드] 이미 삭제한 항목 삭제 시도", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-094] [TC명 : 상세코드 삭제3] [REQ_ID : REQ_ADM_020] [화면 : 기준 정보 > 코드 관리]
         * [기능 : 상세코드 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
         */
        testCases.add(
                new TestCaseDetail<>(
                        BASE_URL + "/detail" + "/[JUnit]ASSET_STATUS" + "/[JUnit]LOCATION"
                                + "/[JUnit]WAREHOUSE_NOT_EXIST",

                        "[상세코드] 존재하지 않는 ID 삭제 시도", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-095] [TC명 : 상세코드 삭제4] [REQ_ID : REQ_ADM_020] [화면 : 기준 정보 > 코드 관리]
         * [기능 : 상세코드 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이스 미존재

        testCases.add(
                new TestCaseDetail<>("N/A", "[상세코드] 비즈니스로직 케이스", null,
                        ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND), status().is4xxClientError()));

        /*
         * [TC_ID : TC-096] [TC명 : 상세코드 삭제5] [REQ_ID : REQ_ADM_020] [화면 : 기준 정보 > 코드 관리]
         * [기능 : 상세코드 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */

        testCases.add(
                new TestCaseDetail<>(BASE_URL + "/", "[상세코드] 잘못된 형식 입력", null,
                        ResponseApi.fail(ErrorCode.PAGE_NOT_FOUND), status().is4xxClientError()));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "코드 삭제 : " + (testCase.getTestName());
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