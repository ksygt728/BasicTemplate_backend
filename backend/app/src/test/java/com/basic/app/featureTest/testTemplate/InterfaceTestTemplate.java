package com.basic.app.featureTest.testTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesForInesrt;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesForSearch;
import com.basic.app.repository.InterfaceRepository;
import com.basic.app.util.TestCaseDetail;
import com.basic.app.util.TestCaseDetailForSearch;
import com.basic.app.util.TestUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스 단위로 테스트 인스턴스 생성
public class InterfaceTestTemplate {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private InterfaceRepository interfaceRepository;

        private List<String> afterAllDeleteList = new ArrayList<>(); // 테스트에 사용할 인터페이스 리스트

        private final String BASE_URL = "/admin/interface";

        long startTime;

        // JSON 문자열 변환 헬퍼
        private String toJson(Object obj) throws Exception {
                return objectMapper.writeValueAsString(obj);
        }

        @BeforeAll
        static void setUpOnce() {
                log.info("📦 테스트 전체 시작 전 단 1회 실행 (@BeforeAll)");
                // DB 스키마 초기화나 공통 설정 작업
        }

        @BeforeEach
        void beforeEach(TestInfo testInfo) {

                log.info("🔄 테스트 시작: " + testInfo.getDisplayName());
                startTime = System.nanoTime();
        }

        @AfterEach
        void afterEach(TestInfo testInfo) {

                long endTime = System.nanoTime();
                long durationMs = (endTime - startTime) / 1_000_000;
                log.info("✅ 테스트 종료: {} (실행 시간: {} ms)", testInfo.getDisplayName(), durationMs);

        }

        @AfterAll
        void cleanUpOnce() {
                log.info("🧹 테스트 전체 종료 후 단 1회 실행 (@AfterAll)");

                // 파일 삭제, 서버 연결 종료 등 자원 해제
                interfaceRepository.deleteAllById(afterAllDeleteList);
        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesForSearch.class)
        @DisplayName("인터페이스_단건_조회")
        void 인터페이스_단건_조회(TestCaseDetailForSearch<?, ?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();
                boolean preSave = testCaseDetail.isPreSave(); // 사전에 먼저 저장이 필요한 테스트 케이스

                // 테스트 후 삭제할 리스트에 추가
                if (testData instanceof InterfaceReqDto dto && dto.getIfId() != null) {
                        afterAllDeleteList.add(dto.getIfId());
                }

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                if (preSave) {
                        MultiValueMap<String, String> multiValueMap = TestUtils
                                        .dtoToMultiValueMap(testData);
                        mockMvc.perform(
                                        post(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                        .params(multiValueMap))
                                        .andExpect(status().isOk())
                                        .andReturn();
                }

                MvcResult actual = mockMvc.perform(get(url))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesForInesrt.class)
        @DisplayName("인터페이스_추가")
        void 인터페이스_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                // 테스트 후 삭제할 리스트에 추가
                if (testData instanceof InterfaceReqDto dto && dto.getIfId() != null) {
                        afterAllDeleteList.add(dto.getIfId());
                }
                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);

                MvcResult actual = mockMvc.perform(
                                post(url)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .params(multiValueMap))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

}
