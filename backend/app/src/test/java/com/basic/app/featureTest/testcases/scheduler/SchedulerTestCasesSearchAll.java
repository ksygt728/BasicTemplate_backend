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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.ScheHReqDto;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.ScheHResDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.entity.ScheH;
import com.basic.app.util.TestCaseDetailSearchForm;

public class SchedulerTestCasesSearchAll implements
        TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/scheduler";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-382] [TC명 : 스케쥴러 추가1] [REQ_ID : REQ_ADM_078] [화면 : 시스템 관리 > 스케쥴러
         * 관리] [기능 : 스케쥴러 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
         */
        String testName_order1 = "정상조회(N건)";
        // searchForm
        ScheMReqDto testData_order1 = new ScheMReqDto();
        testData_order1.setScheId("sche-001");
        // 정렬 없이 페이징만 적용
        PageRequest pageRequest_order1 = PageRequest.of(0, 2000, Sort.by("scheId").descending());

        // Response
        List<ScheMResDto> content = List.of(
                new ScheMResDto(
                        "sche-001_test",
                        "데이터 백업",
                        "매일 자정에 DB 백업 실행",
                        "BACKUP_GROUP",
                        "com.basic.app.job.BackupJob",
                        "executeBackup",
                        "backupTrigger",
                        "0 0 0 * * ?",
                        LocalDateTime.of(2025, 8, 1, 0, 0, 0, 0),
                        LocalDateTime.of(2025, 8, 2, 0, 0, 0, 0),
                        "Y"));

        PageResponse<ScheMResDto> pageResponse_order1 = new PageResponse<>(new PageImpl<>(
                content, pageRequest_order1, 1)); // totalElements=_order10
        pageResponse_order1.setTotalElements(1);
        pageResponse_order1.setTotalPages(1);
        pageResponse_order1.setFirst(true);
        pageResponse_order1.setLast(true);
        ApiResponse<?> expected_order1 = ApiResponse.success(Map.of("data", pageResponse_order1));

        ResultMatcher status_order1 = status().isOk();
        String url_order1 = BASE_URL + "/search";

        /*
         * [TC_ID : TC-367] [TC명 : 스케쥴러 이력 조회1] [REQ_ID : REQ_ADM_075] [화면 : 시스템 관리 >
         * 스케쥴러 관리] [기능 : 스케쥴러 이력 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */

        // String testName_order2 = "정상조회 - 이력(N건)";
        // // searchForm
        // ScheHReqDto testData_order2 = new ScheHReqDto();
        // testData_order2.setScheId("sche-001_test");

        // // ScheH 엔티티의 실제 필드명 사용 (정렬 없이 진행)
        // PageRequest pageRequest_order2 = PageRequest.of(0, 200,
        // Sort.by("startDate").descending());

        // // Response
        // List<ScheHResDto> content2 = List.of(
        // new ScheHResDto(
        // "sche-h-006_test",
        // "sche-001_test", // 백업 작업 재실행
        // "BACKUP_GROUP",
        // LocalDateTime.of(2025, 8, 2, 0, 0, 0, 0),
        // LocalDateTime.of(2025, 8, 2, 0, 7, 12, 800),
        // 432800L, // 7분 12.8초
        // "Y",
        // null),
        // new ScheHResDto(
        // "sche-h-001_test", // logId
        // "sche-001_test", // scheId
        // "BACKUP_GROUP", // scheGroup
        // LocalDateTime.of(2025, 8, 1, 0, 0, 0, 0), // startTime
        // LocalDateTime.of(2025, 8, 1, 0, 5, 30, 500), // endTime
        // 330500L, // execTime (5분 30.5초)
        // "Y", // success
        // null // errorMsg
        // )

        // );

        // PageResponse<ScheHResDto> pageResponse_order2 = new PageResponse<>(new
        // PageImpl<>(
        // content2, pageRequest_order2, 1)); // totalElements=_order10
        // pageResponse_order2.setTotalElements(1);
        // pageResponse_order2.setTotalPages(1);
        // pageResponse_order2.setFirst(true);
        // pageResponse_order2.setLast(true);
        // ApiResponse<?> expected_order2 = ApiResponse.success(Map.of("data",
        // pageResponse_order2));

        // ResultMatcher status_order2 = status().isOk();
        // String url_order2 = BASE_URL + "/history/" + testData_order2.getScheId();

        List<TestCaseDetailSearchForm> testCases = List.of(
                new TestCaseDetailSearchForm<>(url_order1, testName_order1, testData_order1, expected_order1,
                        status_order1, pageRequest_order1));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "스케줄러 조회 : [" + invocationIndex + "] " +
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