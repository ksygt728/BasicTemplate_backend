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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.dto.responseDto.InterfaceResDto;
import com.basic.app.entity.Interface;
import com.basic.app.util.TestCaseDetailSearchForm;

public class InterFaceTestCasesSearchAll implements
        TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/interface";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-097] [TC명 : 인터페이스 기준정보 조회1] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
         * 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : 정상 조회 N건
         * 조회]
         */

        String testName1 = "정상조회(N건)";
        // searchForm
        InterfaceReqDto testData1 = new InterfaceReqDto("IF009", null, null);
        PageRequest pageRequest1 = PageRequest.of(0, 5, Sort.by("ifId").descending());
        List<InterfaceResDto> content = List.of(
                new InterfaceResDto("IF009_SEARCH", "이벤트정보 요청", "/api/v1/events/info"));
        // Response

        PageResponse<InterfaceResDto> pageResponse1 = new PageResponse<>(new PageImpl<>(
                content, pageRequest1, 1)); // totalElements=10
        pageResponse1.setTotalElements(1);
        pageResponse1.setTotalPages(1);
        pageResponse1.setFirst(true);
        pageResponse1.setLast(true);
        ApiResponse<?> expected1 = ApiResponse.success(Map.of("data", pageResponse1));

        ResultMatcher status1 = status().isOk();
        String url1 = BASE_URL + "/search";

        List<TestCaseDetailSearchForm> testCases = List.of(
                new TestCaseDetailSearchForm<>(url1, testName1, testData1, expected1, status1, pageRequest1));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "인터페이스 조회 테스트케이스 : [" + invocationIndex + "] " +
                        (testCase.getTestName());

            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                            ExtensionContext extensionContext) {
                        Class<?> type = parameterContext.getParameter().getType();
                        // TestCaseDetail만 지원
                        return TestCaseDetailSearchForm.class.isAssignableFrom(type);
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