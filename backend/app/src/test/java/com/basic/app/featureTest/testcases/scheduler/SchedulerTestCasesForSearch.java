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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;
import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.responseDto.ScheHResDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TestTimeKeeper;

public class SchedulerTestCasesForSearch implements TestTemplateInvocationContextProvider {

    private final String BASE_URL = "/admin/scheduler";

    @Autowired
    private TestTimeKeeper timeKeeper;

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-362] [TC명 : 스케줄러 리스트 조회1] [REQ_ID : REQ_ADM_074] [화면 : 시스템 관리 >
         * 스케줄러 관리] [기능 : 스케줄러 리스트 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]
         */
        String testName_order1 = "정상 조회 | 스케줄러 조회";
        ScheMResDto testData_order1 = new ScheMResDto(
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
                "Y");
        ApiResponse<?> expected_order1 = ApiResponse.success(Map.of("data", testData_order1));
        String url_order1 = BASE_URL + "/" + testData_order1.getScheId();
        ResultMatcher status_order1 = status().isOk();

        /*
         * [TC_ID : TC-363] [TC명 : 스케줄러 리스트 조회2] [REQ_ID : REQ_ADM_074] [화면 : 시스템 관리 >
         * 스케줄러 관리] [기능 : 스케줄러 리스트 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : ]
         */
        // 해당 케이슨 미존재

        /*
         * [TC_ID : TC-364] [TC명 : 스케줄러 리스트 조회3] [REQ_ID : REQ_ADM_074] [화면 : 시스템 관리 >
         * 스케줄러 관리] [기능 : 스케줄러 리스트 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : ]
         */
        String testName_order3 = "존재하지 않는 ID 조회";
        ScheMResDto testData_order3 = new ScheMResDto(
                "sche-999_test",
                "데이터 백업",
                "매일 자정에 DB 백업 실행",
                "BACKUP_GROUP",
                "com.basic.app.job.BackupJob",
                "executeBackup",
                "backupTrigger",
                "0 0 0 * * ?",
                LocalDateTime.of(2025, 8, 1, 0, 0, 0, 0),
                LocalDateTime.of(2025, 8, 2, 0, 0, 0, 0),
                "Y");
        ApiResponse<?> expected_order3 = ApiResponse.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url_order3 = BASE_URL + "/" + testData_order3.getScheId();
        ResultMatcher status_order3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-365] [TC명 : 스케줄러 리스트 조회4] [REQ_ID : REQ_ADM_074] [화면 : 시스템 관리 >
         * 스케줄러 관리] [기능 : 스케줄러 리스트 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : ]
         */
        // 해당 케이슨 미존재

        /*
         * [TC_ID : TC-366] [TC명 : 스케줄러 리스트 조회5] [REQ_ID : REQ_ADM_074] [화면 : 시스템 관리 >
         * 스케줄러 관리] [기능 : 스케줄러 리스트 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        String testName_order10 = "잘못된 형식 입력 - null 값";
        String testData_order10 = null; // null 값
        ApiResponse<?> expected_order10 = ApiResponse.fail(ErrorCode.PAGE_NOT_FOUND);
        String url_order10 = BASE_URL + "/";
        ResultMatcher status_order10 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url_order1, testName_order1, testData_order1, expected_order1, status_order1),
                new TestCaseDetail<>(url_order3, testName_order3, testData_order3, expected_order3, status_order3),
                new TestCaseDetail<>(url_order10, testName_order10, testData_order10, expected_order10,
                        status_order10));
        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "스케줄러 조회 : " + (testCase.getTestName());

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