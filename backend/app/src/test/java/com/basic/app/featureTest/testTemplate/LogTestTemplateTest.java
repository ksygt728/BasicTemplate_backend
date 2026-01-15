package com.basic.app.featureTest.testTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
import com.basic.app.entity.LogApi;
import com.basic.app.entity.LogError;
import com.basic.app.featureTest.testcases.logApiError.LogApiTestCasesSearchAll;
import com.basic.app.featureTest.testcases.logApiError.LogErrorTestCasesSearchAll;
import com.basic.app.repository.LogApiRepository;
import com.basic.app.repository.LogErrorRepository;
import com.basic.app.util.TestCaseDetailSearchForm;
import com.basic.app.util.TestTimeKeeper;
import com.basic.app.util.TestUtils;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 전역으로 테스트데이터를 사용하기 위해 생성
@Transactional
public class LogTestTemplateTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private LogApiRepository logApiRepository;

        @Autowired
        private LogErrorRepository logErrorRepository;

        @Autowired
        private TestTimeKeeper timeKeeper;

        /*************************************
         * [테스트 데이터 세팅]
         *************************************/
        long startTime;

        private List<LogApi> testDataList_logApi = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트
        private List<LogError> testDataList_logError = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트

        @BeforeAll
        void setUpOnce() {
                initTestData();
                log.info("\n📦 [CBSK-TEST] ***[테스트 전체 시작]*** {}", this.getClass().getName());
        }

        @BeforeEach
        void beforeEach(TestInfo testInfo) {

                log.info("\n\n🔄 [CBSK-TEST] ***[테스트 시작]***: " + testInfo.getDisplayName());
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
                logApiRepository.deleteAllById(testDataList_logApi.stream()
                                .map(LogApi::getLogId)
                                .toList());
                // 파일 삭제, 서버 연결 종료 등 자원 해제
                logErrorRepository.deleteAllById(testDataList_logError.stream()
                                .map(LogError::getErrId)
                                .toList());
        }

        public void initTestData() {
                // 초기 테스트 데이터 설정

                testDataList_logApi = List.of(
                                new LogApi(
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
                logApiRepository.saveAll(testDataList_logApi);

                testDataList_logError = List.of(
                                new LogError(
                                                "9de0b889-dfad-47ce-af60-b7bb7f5c7411", // errId
                                                "testUser", // userId
                                                "192.168.0.1", // ipAddr
                                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)", // userAgent
                                                "/api/test-error", // requestUri
                                                "POST", // httpMethod
                                                "NullPointerException 발생", // errMsg
                                                "java.lang.NullPointerException\n\tat com.basic.app..." // errStack
                                ));
                logErrorRepository.saveAll(testDataList_logError);

        }

        // @TestTemplate
        // @ExtendWith(LogApiErrorTestCasesForSearch.class)
        // @DisplayName("1. API_ERROR로그_단건_조회")
        // void API_ERROR로그_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

        // /* 1. given */

        // String url = testCaseDetail.getUrl();
        // String testCaseName = testCaseDetail.getTestName();
        // Object testData = testCaseDetail.getTestData();
        // ResponseApi<?> expected = testCaseDetail.getExpected();
        // ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

        // /* 2. when */
        // TestUtils.showLogTestCaseStart(testCaseName);

        // MvcResult actual = mockMvc.perform(get(url)
        // .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        // .header("test-token", true))
        // .andExpect(httpStatus)
        // .andReturn();

        // /* 3. then */
        // JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
        // JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

        // TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

        // assertThat(actualToJson).isEqualTo(expectedToJson);

        // }

        @TestTemplate
        @ExtendWith(LogApiTestCasesSearchAll.class)
        @DisplayName("2. API로그_N건_조회")
        void API로그_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {
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
        @ExtendWith(LogErrorTestCasesSearchAll.class)
        @DisplayName("2. 에러로그_N건_조회")
        void 에러로그_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {
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

                expectedToJson = TestUtils.ignoreFields(expectedToJson, "createDate");
                actualToJson = TestUtils.ignoreFields(actualToJson, "createDate");

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(actualToJson).isEqualTo(expectedToJson);

        }

}