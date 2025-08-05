package com.basic.app.featureTest.testcases.interfacesIf;

import org.junit.jupiter.api.extension.*;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.util.TestCaseDetail;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.entity.Interface;
import com.basic.app.exception.ErrorCode;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class InterFaceTestCasesForInesrt implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/interface";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-112] [TC명 : 인터페이스 추가1] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
         */
        String url1 = BASE_URL;
        String testName1 = "정상등록";
        InterfaceReqDto testData1 = new InterfaceReqDto("IF001_TEST", "회원가입 요청", "/api/v1/users/signup");
        ApiResponse<?> expected1 = ApiResponse.success(Map.of("data", testData1));
        ResultMatcher status1 = status().isOk();

        /*
         * [TC_ID : TC-113] [TC명 : 인터페이스 추가2] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 추가] [테스트항목 : 필수값 누락]
         */
        String url2 = BASE_URL;
        String testName2 = "필수값 누락 | IF ID 누락";
        InterfaceReqDto testData2 = new InterfaceReqDto("", "회원가입 요청", "/api/v1/users/signup");
        ApiResponse<?> expected2 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "IF아이디는 필수입니다.");
        ResultMatcher status2 = status().is4xxClientError();

        /*
         * [TC_ID : TC-113] [TC명 : 인터페이스 추가2] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 추가] [테스트항목 : 필수값 누락]
         */
        String url3 = BASE_URL;
        String testName3 = "필수값 누락 | IF명 누락";
        InterfaceReqDto testData3 = new InterfaceReqDto("IF001", "", "/api/v1/users/signup");
        ApiResponse<?> expected3 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "인터페이스명은 필수입니다.");
        ResultMatcher status3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-112] [TC명 : 인터페이스 추가1] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
         */

        String url5 = BASE_URL;
        String testName5 = "존재하는 ID 등록 시도 | 중복";
        InterfaceReqDto testData5 = new InterfaceReqDto("IF003_SEARCH", "회원정보 요청", "/api/v1/users/info");
        ApiResponse<?> expected5 = ApiResponse.fail(ErrorCode.OBJECT_IS_EXISTED);
        ResultMatcher status5 = status().is4xxClientError();

        /*
         * [TC_ID : TC-115] [TC명 : 인터페이스 추가4] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 추가] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : N/A]
         */
        // 테스트 케이스 미존재

        /*
         * [TC_ID : TC-116] [TC명 : 인터페이스 추가5] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 추가] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        // 테스트 케이스 미존재

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url1, testName1, testData1, expected1, status1),
                new TestCaseDetail<>(url2, testName2, testData2, expected2, status2),
                new TestCaseDetail<>(url3, testName3, testData3, expected3, status3),
                new TestCaseDetail<>(url5, testName5, testData5, expected5, status5));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "인터페이스 추가 : " + (testCase.getTestName());
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