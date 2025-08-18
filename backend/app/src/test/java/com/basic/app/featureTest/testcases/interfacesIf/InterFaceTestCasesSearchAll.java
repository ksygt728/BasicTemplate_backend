package com.basic.app.featureTest.testcases.interfacesIf;

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

import com.basic.app.api.ResponseApi;
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
         * [TC_ID : TC-017] [TC명 : 부서 정보 조회1] [REQ_ID : REQ_ADM_005] [화면 : 조직 관리 > 부서
         * 관리] [기능 : 부서 정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : N건 조회]
         */

        String testName_order1 = "정상조회(N건)";
        // searchForm
        InterfaceReqDto testData_order1 = new InterfaceReqDto("IF009", null, null);
        PageRequest pageRequest_order1 = PageRequest.of(0, 5, Sort.by("ifId").descending());
        List<InterfaceResDto> content = List.of(
                new InterfaceResDto("IF009_SEARCH", "이벤트정보 요청", "/api/v1/events/info"));
        // Response

        PageResponse<InterfaceResDto> pageResponse_order1 = new PageResponse<>(new PageImpl<>(
                content, pageRequest_order1, 1)); // totalElements=10
        pageResponse_order1.setTotalElements(1);
        pageResponse_order1.setTotalPages(1);
        pageResponse_order1.setFirst(true);
        pageResponse_order1.setLast(true);
        ResponseApi<?> expected_order1 = ResponseApi.success(Map.of("data", pageResponse_order1));

        ResultMatcher status_order1 = status().isOk();
        String url_order1 = BASE_URL + "/search";

        List<TestCaseDetailSearchForm> testCases = List.of(
                new TestCaseDetailSearchForm<>(url_order1, testName_order1, testData_order1, expected_order1,
                        status_order1, pageRequest_order1));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "인터페이스 조회 : [" + invocationIndex + "] " +
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