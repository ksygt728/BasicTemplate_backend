package com.basic.app.featureTest.testcases.interfacesIf;

import org.junit.jupiter.api.extension.*;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.util.TestCaseDetail;

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.entity.Interface;
import com.basic.app.exception.ErrorCode;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class InterFaceTestCasesForDelete implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/interface";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-122] [TC명 : 인터페이스 삭제1] [REQ_ID : REQ_ADM_026] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 삭제] [테스트항목 : 정상 삭제] [테스트 상세 : ]
         */
        // 해당케이스 미존재

        String testName1 = "정상 삭제";
        InterfaceReqDto testData1 = new InterfaceReqDto("IF013_SEARCH", "상품평 요청", "/api/v1/reviews/product");
        String url1 = BASE_URL + "/" + testData1.getIfId();
        ResponseApi<?> expected1 = ResponseApi.success(Map.of("data", "success"));
        ResultMatcher status1 = status().isOk();

        /*
         * [TC_ID : TC-123] [TC명 : 인터페이스 삭제2] [REQ_ID : REQ_ADM_026] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 삭제] [테스트항목 : 이미 삭제한 항목 삭제 시도] [테스트 상세 : ]
         */
        // 해당케이스 미존재

        String testName2 = "이미 삭제한 항목 삭제 시도";
        InterfaceReqDto testData2 = new InterfaceReqDto("IF018_SEARCH", "로그인정보 요청", "/api/v1/auth/login");
        String url2 = BASE_URL + "/" + testData2.getIfId();
        ResponseApi<?> expected2 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        ResultMatcher status2 = status().is4xxClientError();
        /*
         * [TC_ID : TC-124] [TC명 : 인터페이스 삭제3] [REQ_ID : REQ_ADM_026] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 삭제] [테스트항목 : 존재하지 않는 ID 삭제 시도] [테스트 상세 : ]
         */

        String testName3 = "존재하지 않는 ID 삭제 시도";
        InterfaceReqDto testData3 = new InterfaceReqDto("IF019_SEARCH_TEST", "상품페이지 요청", "/api/v1/items/product");
        String url3 = BASE_URL + "/" + testData3.getIfId();
        ResponseApi<?> expected3 = ResponseApi.fail(ErrorCode.OBJECT_NOT_FOUND);
        ResultMatcher status3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-125] [TC명 : 인터페이스 삭제4] [REQ_ID : REQ_ADM_026] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 삭제] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당케이스 미존재

        /*
         * [TC_ID : TC-126] [TC명 : 인터페이스 삭제5] [REQ_ID : REQ_ADM_026] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 삭제] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        // 해당케이스 미존재

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url1, testName1, testData1, expected1, status1),
                new TestCaseDetail<>(url2, testName2, testData2, expected2, status2),
                new TestCaseDetail<>(url3, testName3, testData3, expected3, status3));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "인터페이스 삭제 : " + (testCase.getTestName());
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
