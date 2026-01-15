package com.basic.app.featureTest.testTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.entity.ScheH;
import com.basic.app.entity.ScheM;
import com.basic.app.featureTest.testcases.scheduler.SchedulerTestCasesForDelete;
import com.basic.app.featureTest.testcases.scheduler.SchedulerTestCasesForInesrt;
import com.basic.app.featureTest.testcases.scheduler.SchedulerTestCasesForSearch;
import com.basic.app.featureTest.testcases.scheduler.SchedulerTestCasesForUpdate;
import com.basic.app.featureTest.testcases.scheduler.SchedulerTestCasesSearchAll;
import com.basic.app.repository.SchedulerHistoryRepository;
import com.basic.app.repository.SchedulerRepository;
import com.basic.app.util.Status;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TestCaseDetailSearchForm;
import com.basic.app.util.TestUtils;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스 단위로 테스트 인스턴스 생성
@Transactional
public class SchedulerTestTemplateTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private SchedulerRepository schedulerRepository;

        @Autowired
        private SchedulerHistoryRepository schedulerHistoryRepository;

        /*************************************
         * [테스트 데이터 세팅]
         *************************************/
        long startTime;

        private List<ScheM> testDataList = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트
        private List<ScheH> testDataHistoryList = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트

        @BeforeAll
        void setUpOnce() {
                initTestData();
                log.info("\n📦 [CBSK-TEST] ***[테스트 전체 시작]*** {}", this.getClass().getName());
                // DB 스키마 초기화나 공통 설정 작업
        }

        @BeforeEach
        void beforeEach(TestInfo testInfo) {

                log.info("\n\n🔄 [CBSK-TEST] ***[테스트 시작]*** " + testInfo.getDisplayName());
                startTime = System.nanoTime();
        }

        @AfterEach
        void afterEach(TestInfo testInfo) {

                long endTime = System.nanoTime();
                long durationMs = (endTime - startTime) / 1_000_000;
                log.info("\n✅ [CBSK-TEST] ***[테스트 종료]*** (실행 시간: {} ms)\n", durationMs);

        }

        @AfterAll
        void cleanUpOnce() {
                log.info("\n🧹 [CBSK-TEST] ***[테스트 전체 종료]*** {}", this.getClass().getName());

                // 파일 삭제, 서버 연결 종료 등 자원 해제
                schedulerRepository.deleteAllById(testDataList.stream()
                                .map(ScheM::getScheId)
                                .toList());

                schedulerHistoryRepository.deleteAllById(testDataHistoryList.stream()
                                .map(ScheH::getLogId)
                                .toList());

        }

        public void initTestData() {

                ScheM scheM1 = new ScheM(
                                "sche-005_test",
                                "세션 클리너",
                                "매일 1시간마다 만료 세션 삭제",
                                "SESSION_GROUP",
                                "com.basic.app.job.SessionCleanerJob",
                                "cleanExpiredSessions",
                                "sessionTrigger",
                                "0 0 * * * ?",
                                LocalDateTime.of(2025, 8, 1, 15, 0, 0, 0),
                                LocalDateTime.of(2025, 8, 1, 16, 0, 0, 0),
                                "Y");
                scheM1.setSts(Status.NAGATIVE);

                testDataList = List.of(
                                new ScheM(
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
                                                "Y"),
                                new ScheM(
                                                "sche-002_test",
                                                "메일 발송",
                                                "매일 오전 9시 뉴스레터 발송",
                                                "MAIL_GROUP",
                                                "com.basic.app.job.MailJob",
                                                "sendNewsletter",
                                                "mailTrigger",
                                                "0 0 9 * * ?",
                                                LocalDateTime.of(2025, 8, 1, 9, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 2, 9, 0, 0, 0),
                                                "Y"),
                                new ScheM(
                                                "sche-003_test",
                                                "로그 정리",
                                                "매주 일요일 새벽 3시 로그 삭제",
                                                "CLEANUP_GROUP",
                                                "com.basic.app.job.LogCleanupJob",
                                                "cleanLogs",
                                                "cleanupTrigger",
                                                "0 0 3 ? * SUN",
                                                LocalDateTime.of(2025, 8, 3, 3, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 10, 3, 0, 0, 0),
                                                "N"),
                                new ScheM(
                                                "sche-004_test",
                                                "리포트 생성",
                                                "매월 1일 새벽 2시 리포트 생성",
                                                "REPORT_GROUP",
                                                "com.basic.app.job.ReportJob",
                                                "generateReport",
                                                "reportTrigger",
                                                "0 0 2 1 * ?",
                                                LocalDateTime.of(2025, 8, 1, 2, 0, 0, 0),
                                                LocalDateTime.of(2025, 9, 1, 2, 0, 0, 0),
                                                "Y"),
                                scheM1 // 삭제 데이터 테스
                );

                schedulerRepository.saveAll(testDataList);

                // ScheH 테이블 테스트 데이터 10개 생성
                testDataHistoryList = List.of(
                                new ScheH(
                                                "sche-h-001_test", // logId
                                                "sche-001_test", // scheId
                                                "BACKUP_GROUP", // scheGroup
                                                LocalDateTime.of(2025, 8, 1, 0, 0, 0, 0), // startTime
                                                LocalDateTime.of(2025, 8, 1, 0, 5, 30, 500), // endTime
                                                330500L, // execTime (5분 30.5초)
                                                "Y", // success
                                                null // errorMsg
                                ),
                                new ScheH(
                                                "sche-h-002_test",
                                                "sche-002_test",
                                                "MAIL_GROUP",
                                                LocalDateTime.of(2025, 8, 1, 9, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 1, 9, 2, 15, 200),
                                                135200L, // 2분 15.2초
                                                "Y",
                                                null),
                                new ScheH(
                                                "sche-h-003_test",
                                                "sche-003_test",
                                                "CLEANUP_GROUP",
                                                LocalDateTime.of(2025, 8, 3, 3, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 3, 3, 15, 45, 300),
                                                945300L, // 15분 45.3초
                                                "Y",
                                                null),
                                new ScheH(
                                                "sche-h-004_test",
                                                "sche-004_test",
                                                "REPORT_GROUP",
                                                LocalDateTime.of(2025, 8, 1, 2, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 1, 2, 0, 0, 0), // 실패로 즉시 종료
                                                500L, // 0.5초
                                                "N", // 실패
                                                "데이터베이스 연결 실패: Connection timeout"),
                                new ScheH(
                                                "sche-h-005_test",
                                                "sche-005_test",
                                                "SESSION_GROUP",
                                                LocalDateTime.of(2025, 8, 1, 15, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 1, 15, 0, 30, 100),
                                                30100L, // 30.1초
                                                "Y",
                                                null),
                                new ScheH(
                                                "sche-h-006_test",
                                                "sche-001_test", // 백업 작업 재실행
                                                "BACKUP_GROUP",
                                                LocalDateTime.of(2025, 8, 2, 0, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 2, 0, 7, 12, 800),
                                                432800L, // 7분 12.8초
                                                "Y",
                                                null),
                                new ScheH(
                                                "sche-h-007_test",
                                                "sche-002_test", // 메일 발송 재실행
                                                "MAIL_GROUP",
                                                LocalDateTime.of(2025, 8, 2, 9, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 2, 9, 0, 0, 0), // 즉시 실패
                                                1200L, // 1.2초
                                                "N",
                                                "SMTP 서버 오류: Unable to connect to mail.example.com:587"),
                                new ScheH(
                                                "sche-h-008_test",
                                                "sche-005_test", // 세션 클리너 재실행
                                                "SESSION_GROUP",
                                                LocalDateTime.of(2025, 8, 1, 16, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 1, 16, 1, 5, 600),
                                                65600L, // 1분 5.6초
                                                "Y",
                                                null),
                                new ScheH(
                                                "sche-h-009_test",
                                                "sche-004_test", // 리포트 생성 재시도
                                                "REPORT_GROUP",
                                                LocalDateTime.of(2025, 8, 1, 2, 30, 0, 0),
                                                LocalDateTime.of(2025, 8, 1, 2, 45, 22, 400),
                                                922400L, // 15분 22.4초
                                                "Y",
                                                null),
                                new ScheH(
                                                "sche-h-010_test",
                                                "sche-003_test", // 로그 정리 재실행
                                                "CLEANUP_GROUP",
                                                LocalDateTime.of(2025, 8, 10, 3, 0, 0, 0),
                                                LocalDateTime.of(2025, 8, 10, 3, 0, 0, 0), // 즉시 실패
                                                2500L, // 2.5초
                                                "N",
                                                "디스크 공간 부족: /var/log 파티션에 여유 공간이 없습니다"));
                schedulerHistoryRepository.saveAll(testDataHistoryList);

        }

        @TestTemplate
        @ExtendWith(SchedulerTestCasesForSearch.class)
        @DisplayName("1. 스케줄러_단건_조회")
        void 스케줄러_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */

                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ResponseApi<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                MvcResult actual = mockMvc.perform(get(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .header("test-token", true))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(actualToJson).isEqualTo(expectedToJson);

        }

        @TestTemplate
        @ExtendWith(SchedulerTestCasesSearchAll.class)
        @DisplayName("2. 스케줄러_N건_조회")
        void 스케줄러_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {
                /* 1. given */

                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ResponseApi<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();
                PageRequest pageRequest = testCaseDetail.getPageRequest();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                // 파라미터 변환
                MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);
                multiValueMap.add("page", String.valueOf(pageRequest.getPageNumber()));
                multiValueMap.add("size", String.valueOf(pageRequest.getPageSize()));
                multiValueMap.add("sort", pageRequest.getSort().toString());

                MvcResult actual = mockMvc.perform(get(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .header("test-token", true)
                                .params(multiValueMap))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(actualToJson).isEqualTo(expectedToJson);

        }

        @TestTemplate
        @ExtendWith(SchedulerTestCasesForInesrt.class)
        @DisplayName("3. 스케줄러_추가")
        void 스케줄러_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ResponseApi<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                String jsonContent = TestUtils.objectToJson(testData);

                MvcResult actual = mockMvc.perform(
                                post(url)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("test-token", true)
                                                .content(jsonContent))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(actualToJson).isEqualTo(expectedToJson);

        }

        @TestTemplate
        @ExtendWith(SchedulerTestCasesForUpdate.class)
        @DisplayName("4. 스케줄러_수정")
        void 스케줄러_수정(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ResponseApi<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                // Handle both ScheMReqDto and ScheMResDto test data types
                ScheMReqDto reqDto;
                if (testData instanceof ScheMReqDto) {
                        // Test data is already ScheMReqDto, use directly
                        reqDto = (ScheMReqDto) testData;
                } else if (testData instanceof ScheMResDto) {
                        // Convert ScheMResDto to ScheMReqDto for JSON serialization (excluding
                        // lastExecTime, nextExecTime)
                        ScheMResDto resDto = (ScheMResDto) testData;
                        reqDto = ScheMReqDto.builder()
                                        .scheId(resDto.getScheId())
                                        .scheName(resDto.getScheName())
                                        .description(resDto.getDescription())
                                        .scheGroup(resDto.getScheGroup())
                                        .className(resDto.getClassName())
                                        .methodName(resDto.getMethodName())
                                        .triggerName(resDto.getTriggerName())
                                        .cronExp(resDto.getCronExp())
                                        .useYn(resDto.getUseYn())
                                        .build();
                } else {
                        throw new IllegalArgumentException("Unsupported test data type: " + testData.getClass());
                }

                String jsonContent = TestUtils.objectToJson(reqDto);

                MvcResult actual = mockMvc.perform(
                                put(url)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("test-token", true)
                                                .content(jsonContent))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(actualToJson).isEqualTo(expectedToJson);

        }

        @TestTemplate
        @ExtendWith(SchedulerTestCasesForDelete.class)
        @DisplayName("5. 스케줄러_삭제")
        void 스케줄러_삭제(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ResponseApi<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                MvcResult actual = mockMvc.perform(
                                delete(url)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .header("test-token", true))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(actualToJson).isEqualTo(expectedToJson);

                // departmentRepository.findById(((InterfaceReqDto) testData).getIfId())
                // .ifPresent(actualSts ->
                // assertThat(Status.NAGATIVE).isEqualTo(actualSts.getSts()));

        }

}
