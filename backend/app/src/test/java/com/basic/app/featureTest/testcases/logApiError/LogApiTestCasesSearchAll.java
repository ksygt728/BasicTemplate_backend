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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ResponseApi;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.LogApiReqDto;
import com.basic.app.dto.responseDto.LogApiResDto;
import com.basic.app.util.TestCaseDetailSearchForm;
import com.basic.app.util.TestTimeKeeper;

public class LogApiTestCasesSearchAll implements
        TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/log";

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
        TestTimeKeeper timeKeeper = new TestTimeKeeper();
        String testName_order1 = "정상조회(N건)";
        // searchForm
        LogApiReqDto testData_order1 = new LogApiReqDto().builder()
                .logId("9de0b111")
                .build();

        PageRequest pageRequest_order1 = PageRequest.of(0, 5, Sort.by("startDate").descending());
        List<LogApiResDto> content = List.of(
                new LogApiResDto(
                        "9de0b111-dfad-47ce-af60-b7bb7f5c7480",
                        "testUser",
                        timeKeeper.convertStringToLocalDateTime("2025-07-23 10:15:30.000"),
                        timeKeeper.convertStringToLocalDateTime("2025-07-23 10:15:31.000"),
                        "192.168.0.1",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
                        "/api/test",
                        "GET",
                        "{\"param\":\"value\"}",
                        "{\"result\":\"success\"}",
                        "200",
                        1000L));
        // Response

        PageResponse<LogApiResDto> pageResponse_order1 = new PageResponse<>(new PageImpl<>(
                content, pageRequest_order1, 1)); // totalElements=10
        pageResponse_order1.setTotalElements(1);
        pageResponse_order1.setTotalPages(1);
        pageResponse_order1.setFirst(true);
        pageResponse_order1.setLast(true);
        ResponseApi<?> expected_order1 = ResponseApi.success(Map.of("data", pageResponse_order1));

        ResultMatcher status_order1 = status().isOk();
        String url_order1 = BASE_URL + "/api-log/search";

        List<TestCaseDetailSearchForm> testCases = List.of(
                new TestCaseDetailSearchForm<>(url_order1, testName_order1, testData_order1, expected_order1,
                        status_order1, pageRequest_order1));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + " 로그 API 조회 : " +
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