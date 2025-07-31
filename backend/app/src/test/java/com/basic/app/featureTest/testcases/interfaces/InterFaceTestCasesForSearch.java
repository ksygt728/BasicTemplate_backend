package com.basic.app.featureTest.testcases.interfaces;

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

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.entity.Interface;
import com.basic.app.exception.ErrorCode;
import com.basic.app.repository.InterfaceRepository;
import com.basic.app.util.TestCaseDetail;

public class InterFaceTestCasesForSearch implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/interface";

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        
        // [TC_ID : TC-097] [TC명 : 인터페이스 기준정보 조회1] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
        // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : 정상 조회 1건 조회]
        String testName1 = "정상조회";
        InterfaceReqDto testData1 = new InterfaceReqDto("IF002_SEARCH", "주문페이지 요청", "/api/v1/orders/request");
        ApiResponse<?> expected1 = ApiResponse.success(Map.of("data", testData1));
        ResultMatcher status1 = status().isOk();
        String url1 = BASE_URL + "/" + testData1.getIfId();

        InterfaceReqDto searchForm1 = new InterfaceReqDto("IF002_SEARCH", "상품페이지 요청", "/api/v1/items/product");
        boolean preSave1 = true; // 사전에 먼저 저장이 필요한 테스트 케이스

        // [TC_ID : TC-098] [TC명 : 인터페이스 기준정보 조회2] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
        // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : N/A]
        // 테스트 케이스 미존재

        // [TC_ID : TC-099] [TC명 : 인터페이스 기준정보 조회3] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
        // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : 존재하지
        // 않는ID 조회]
        String testName3 = "존재하지 않는 ID 조회";
        InterfaceReqDto testData3 = new InterfaceReqDto("IF_ID_NOT_EXIST", "상품페이지 요청", "/api/v1/items/product");
        ApiResponse<?> expected3 = ApiResponse.fail(ErrorCode.OBJECT_NOT_FOUND);
        ResultMatcher status3 = status().is4xxClientError();
        String url3 = BASE_URL + "/" + testData3.getIfId();

        InterfaceReqDto searchForm3 = null;
        boolean preSave3 = false; // 사전에 먼저 저장이 필요한 테스트 케이스

        // [TC_ID : TC-100] [TC명 : 인터페이스 기준정보 조회4] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
        // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : N/A]
        // 테스트 케이스 미존재

        // [TC_ID : TC-101] [TC명 : 인터페이스 기준정보 조회5] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
        // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : 잘못된 형식 입력]
        String testName5 = "잘못된 형식 입력";
        InterfaceReqDto testData5 = null; // 잘못된 형식 입력을 위한 DTO는 null로 설정
        ApiResponse<?> expected5 = ApiResponse.fail(ErrorCode.PAGE_NOT_FOUND);
        String url5 = BASE_URL + "/";
        ResultMatcher status5 = status().is4xxClientError();

        InterfaceReqDto searchForm5 = null;
        boolean preSave5 = false; // 사전에 먼저 저장이 필요한 테스트 케이스

        // 기존 변수들을 활용하여 TestCaseDetailForSearch 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url1, testName1, testData1, expected1, status1),
                new TestCaseDetail<>(url3, testName3, testData3, expected3, status3),
                new TestCaseDetail<>(url5, testName5, testData5, expected5, status5));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "인터페이스 조회 테스트케이스 : [" + invocationIndex + "] " + (testCase.getTestName());

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