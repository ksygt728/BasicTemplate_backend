package com.basic.app.featureTest.testcases.interfacesIf;

import org.junit.jupiter.api.extension.*;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.util.TestCaseDetail;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.exception.ErrorCode;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class InterFaceTestCasesForUpdate implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/interface";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-117] [TC명 : 인터페이스 수정1] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 정상 수정] [테스트 상세 : 정상 등록]
         */
        String url1 = BASE_URL;
        String testName1 = "비정상수정 | 키수정 - 키는 수정할 수 없음";
        InterfaceReqDto testData1 = new InterfaceReqDto("IF005_SEARCH_(수정)", "배송정보 요청", "/api/v1/items/product");
        ApiResponse<?> expected1 = ApiResponse.fail(ErrorCode.OBJECT_NOT_FOUND);
        ResultMatcher status1 = status().is4xxClientError();

        /*
         * [TC_ID : TC-117] [TC명 : 인터페이스 수정1] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 정상 수정] [테스트 상세 : 정상 등록]
         */
        String url2 = BASE_URL;
        String testName2 = "정상수정 | 이름 수정";
        InterfaceReqDto testData2 = new InterfaceReqDto("IF005_SEARCH", "배송정보 요청(수정)", "/api/v1/items/product");
        ApiResponse<?> expected2 = ApiResponse.success(Map.of("data", testData2));
        ResultMatcher status2 = status().isOk();

        /*
         * [TC_ID : TC-117] [TC명 : 인터페이스 수정1] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 정상 수정] [테스트 상세 : 정상 등록]
         */
        String url3 = BASE_URL;
        String testName3 = "정상수정 | Text 수정";
        InterfaceReqDto testData3 = new InterfaceReqDto("IF005_SEARCH", "배송정보 요청", "/api/v1/items/product/update");
        ApiResponse<?> expected3 = ApiResponse.success(Map.of("data", testData3));
        ResultMatcher status3 = status().isOk();

        /*
         * [TC_ID : TC-117] [TC명 : 인터페이스 수정1] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 정상 수정] [테스트 상세 : 정상 등록]
         */
        String url4 = BASE_URL;
        String testName4 = "정상수정 | 이름 + Text 수정";
        InterfaceReqDto testData4 = new InterfaceReqDto("IF005_SEARCH", "배송정보 요청(수정)", "/api/v1/items/product/update");
        ApiResponse<?> expected4 = ApiResponse.success(Map.of("data", testData4));
        ResultMatcher status4 = status().isOk();

        /*
         * [TC_ID : TC-118] [TC명 : 인터페이스 수정2] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 필수값 누락] [테스트 상세 : IF ID 누락 IF명 누락]
         */
        String url5 = BASE_URL;
        String testName5 = "필수값 누락 | IF ID 누락";
        InterfaceReqDto testData5 = new InterfaceReqDto("", "배송정보 요청(수정)", "/api/v1/items/product/update");
        ApiResponse<?> expected5 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "IF아이디는 필수입니다.");
        ResultMatcher status5 = status().is4xxClientError();

        /*
         * [TC_ID : TC-118] [TC명 : 인터페이스 수정2] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 필수값 누락] [테스트 상세 : IF ID 누락 IF명 누락]
         */
        String url6 = BASE_URL;
        String testName6 = "필수값 누락 | IF명 누락";
        InterfaceReqDto testData6 = new InterfaceReqDto("IF005_SEARCH", "", "/api/v1/items/product/update");
        ApiResponse<?> expected6 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "인터페이스명은 필수입니다.");
        ResultMatcher status6 = status().is4xxClientError();

        /*
         * [TC_ID : TC-119] [TC명 : 인터페이스 수정3] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : 존재하는 ID 등록 시도]
         */
        String url7 = BASE_URL;
        String testName7 = "존재하지 않는 ID 수정 시도 | 키 미존재";
        InterfaceReqDto testData7 = new InterfaceReqDto("IF005_SEARCH_NOT_EXIST", "배송정보 요청(수정)_수정시도",
                "/api/v1/items/product/update");
        ApiResponse<?> expected7 = ApiResponse.fail(ErrorCode.OBJECT_NOT_FOUND);
        ResultMatcher status7 = status().is4xxClientError();
        /*
         * [TC_ID : TC-120] [TC명 : 인터페이스 수정4] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : N/A]
         */
        // 해당 케이스 미존재

        /*
         * [TC_ID : TC-121] [TC명 : 인터페이스 수정5] [REQ_ID : REQ_ADM_025] [화면 : 기준 정보 > 인터페이스
         * 관리] [기능 : 인터페이스 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : N/A]
         */
        // 해당 케이스 미존재

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url1, testName1, testData1, expected1, status1),
                new TestCaseDetail<>(url2, testName2, testData2, expected2, status2),
                new TestCaseDetail<>(url3, testName3, testData3, expected3, status3),
                new TestCaseDetail<>(url4, testName4, testData4, expected4, status4),
                new TestCaseDetail<>(url5, testName5, testData5, expected5, status5),
                new TestCaseDetail<>(url6, testName6, testData6, expected6, status6),
                new TestCaseDetail<>(url7, testName7, testData7, expected7, status7));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "인터페이스 수정 : " + (testCase.getTestName());
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