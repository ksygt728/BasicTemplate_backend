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
import org.springframework.test.web.servlet.ResultMatcher;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.exception.ErrorCode;
import com.basic.app.util.TestCaseDetail;

public class SchedulerTestCasesForUpdate implements TestTemplateInvocationContextProvider {
    private final String BASE_URL = "/admin/scheduler";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        /*
         * [TC_ID : TC-387] [TC명 : 스케줄러 수정1] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 정상 수정] [테스트 상세 : ]
         */
        String testName_order1 = "정상 수정 - 백업 스케줄러 정보 수정";
        ScheMReqDto testData_order1 = new ScheMReqDto(
                "sche-001_test", // 기존 테스트 데이터 ID
                "데이터 백업 (수정됨)",
                "매일 새벽 2시 DB 백업 실행 (수정됨)",
                "BACKUP_GROUP_MODIFIED",
                "com.basic.app.job.BackupJobModified",
                "executeBackupModified",
                "backupTriggerModified",
                "0 0 2 * * ?", // 새벽 2시로 변경
                "Y");
        ApiResponse<?> expected_order1 = ApiResponse.success(Map.of("data", new ScheMResDto(
                "sche-001_test", // 기존 테스트 데이터 ID
                "데이터 백업 (수정됨)",
                "매일 새벽 2시 DB 백업 실행 (수정됨)",
                "BACKUP_GROUP_MODIFIED",
                "com.basic.app.job.BackupJobModified",
                "executeBackupModified",
                "backupTriggerModified",
                "0 0 2 * * ?", // 새벽 2시로 변경
                null, null,
                "Y")));
        String url_order1 = BASE_URL;
        ResultMatcher status_order1 = status().isOk();

        /*
         * [TC_ID : TC-388] [TC명 : 스케줄러 수정2] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 필수값 누락] [테스트 상세 : scheName 누락 ]
         */
        String testName_order2 = "필수값 누락 | scheName 누락";
        ScheMReqDto testData_order2 = new ScheMReqDto(
                "sche-002_test",
                null, // scheName 누락
                "메일 발송 스케줄러 수정",
                "MAIL_GROUP",
                "com.basic.app.job.MailJob",
                "sendNewsletter",
                "mailTrigger",
                "0 0 10 * * ?",
                "Y");
        ApiResponse<?> expected_order2 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "스케줄명 필수입니다.");
        String url_order2 = BASE_URL;
        ResultMatcher status_order2 = status().is4xxClientError();

        /*
         * [TC_ID : TC-388] [TC명 : 스케줄러 수정2] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 필수값 누락] [테스트 상세 : className 누락]
         */
        String testName_order6 = "필수값 누락 | className 누락";
        ScheMReqDto testData_order6 = new ScheMReqDto(
                "sche-004_test",
                "리포트 생성 (수정)",
                "리포트 생성 스케줄러 수정",
                "REPORT_GROUP",
                null, // className 누락
                "generateReport",
                "reportTrigger",
                "0 0 3 1 * ?",
                "Y");

        ApiResponse<?> expected_order6 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "클래스명은 필수입니다.");
        String url_order6 = BASE_URL;
        ResultMatcher status_order6 = status().is4xxClientError();

        /*
         * [TC_ID : TC-389] [TC명 : 스케줄러 수정3] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 존재하지 않는 ID 수정 시도] [테스트 상세 : ]
         */
        String testName_order3 = "존재하지 않는 ID 수정 시도";
        ScheMReqDto testData_order3 = new ScheMReqDto(
                "sche-999_nonexistent", // 존재하지 않는 ID
                "존재하지 않는 스케줄러",
                "존재하지 않는 스케줄러 수정 시도",
                "NONEXISTENT_GROUP",
                "com.basic.app.job.NonexistentJob",
                "executeNonexistent",
                "nonexistentTrigger",
                "0 0 12 * * ?",
                "Y");
        ApiResponse<?> expected_order3 = ApiResponse.fail(ErrorCode.OBJECT_NOT_FOUND);
        String url_order3 = BASE_URL;
        ResultMatcher status_order3 = status().is4xxClientError();

        /*
         * [TC_ID : TC-390] [TC명 : 스케줄러 수정4] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : 활성 상태를 비활성으로 변경]
         */
        String testName_order4 = "비즈니스로직 케이스 | 활성 상태를 비활성으로 변경";
        ScheMReqDto testData_order4 = new ScheMReqDto(
                "sche-002_test", // 기존 활성 스케줄러
                "메일 발송 (비활성화)",
                "메일 발송 스케줄러 비활성화",
                "MAIL_GROUP",
                "com.basic.app.job.MailJob",
                "sendNewsletter",
                "mailTrigger",
                "0 0 9 * * ?",
                "N" // 비활성으로 변경
        );
        ApiResponse<?> expected_order4 = ApiResponse.success(Map.of("data", new ScheMResDto(
                "sche-002_test", // 기존 활성 스케줄러
                "메일 발송 (비활성화)",
                "메일 발송 스케줄러 비활성화",
                "MAIL_GROUP",
                "com.basic.app.job.MailJob",
                "sendNewsletter",
                "mailTrigger",
                "0 0 9 * * ?",
                null, null,
                "N" // 비활성으로 변경
        )));
        String url_order4 = BASE_URL;
        ResultMatcher status_order4 = status().isOk();

        /*
         * [TC_ID : TC-390] [TC명 : 스케줄러 수정4] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 비즈니스로직 케이스] [테스트 상세 : 잘못된 Cron 표현식]
         */

        String testName_order5 = "비즈니스로직 케이스 | 잘못된 Cron 표현식";
        ScheMReqDto testData_order5 = new ScheMReqDto(
                "sche-003_test",
                "로그 정리 (수정)",
                "로그 정리 스케줄러 수정",
                "CLEANUP_GROUP",
                "com.basic.app.job.LogCleanupJob",
                "cleanLogs",
                "cleanupTrigger",
                "INVALID_CRON_FORMAT", // 잘못된 Cron 표현식
                "Y");
        ApiResponse<?> expected_order5 = ApiResponse.fail(ErrorCode.SCHEDULER_CREATE_FAILED);
        String url_order5 = BASE_URL;
        ResultMatcher status_order5 = status().is4xxClientError();

        /*
         * [TC_ID : TC-391] [TC명 : 스케줄러 수정5] [REQ_ID : REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러
         * 관리] [기능 : 스케줄러 수정] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : ]
         */
        String testName_order7 = "잘못된 형식 입력";
        ScheMReqDto testData_order7 = new ScheMReqDto(
                "sche-003_test",
                "로그 정리 (수정)",
                "로그 정리 스케줄러 수정",
                "CLEANUP_GROUP",
                "com.basic.app.job.LogCleanupJob",
                "cleanLogs",
                "cleanupTrigger",
                "INVALID_CRON_FORMAT", // 잘못된 Cron 표현식
                "Y");
        ApiResponse<?> expected_order7 = ApiResponse.fail(ErrorCode.PAGE_NOT_FOUND);
        String url_order7 = BASE_URL + "/";
        ResultMatcher status_order7 = status().is4xxClientError();

        // 기존 변수들을 활용하여 TestCaseDetail 객체로 리스트 생성
        List<TestCaseDetail> testCases = List.of(
                new TestCaseDetail<>(url_order1, testName_order1, testData_order1, expected_order1, status_order1),
                new TestCaseDetail<>(url_order2, testName_order2, testData_order2, expected_order2, status_order2),
                new TestCaseDetail<>(url_order6, testName_order6, testData_order6, expected_order6, status_order6),
                new TestCaseDetail<>(url_order3, testName_order3, testData_order3, expected_order3, status_order3),
                new TestCaseDetail<>(url_order4, testName_order4, testData_order4, expected_order4, status_order4),
                new TestCaseDetail<>(url_order5, testName_order5, testData_order5, expected_order5, status_order5),
                new TestCaseDetail<>(url_order7, testName_order7, testData_order7, expected_order7, status_order7));

        // 각 케이스에 대한 InvocationContext 생성
        return testCases.stream().map(testCase -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "[" + invocationIndex + "] " + "스케줄러 수정 : " + (testCase.getTestName());
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