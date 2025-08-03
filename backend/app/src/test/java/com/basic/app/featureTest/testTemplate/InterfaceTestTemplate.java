package com.basic.app.featureTest.testTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ApiResponse;
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.entity.Interface;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesForDelete;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesForInesrt;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesForSearch;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesForUpdate;
import com.basic.app.featureTest.testcases.interfaces.InterFaceTestCasesSearchAll;
import com.basic.app.repository.InterfaceRepository;
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
public class InterfaceTestTemplate {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private InterfaceRepository interfaceRepository;

        private List<Interface> testDataList = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트

        long startTime;

        @BeforeAll
        void setUpOnce() {
                initTestData();
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
                interfaceRepository.deleteAllById(testDataList.stream()
                                .map(Interface::getIfId)
                                .toList());
        }

        public void initTestData() {
                // 초기 테스트 데이터 설정
                Interface if18 = new Interface("IF018_SEARCH", "로그인정보 요청", "/api/v1/auth/login");
                if18.setSts(Status.NAGATIVE);
                Interface if19 = new Interface("IF019_SEARCH", "로그아웃 요청", "/api/v1/auth/logout");
                if19.setSts(Status.NAGATIVE);
                Interface if20 = new Interface("IF020_SEARCH", "회원가입 요청", "/api/v1/auth/register");
                if20.setSts(Status.NAGATIVE);

                testDataList = List.of(
                                new Interface("IF001_SEARCH", "상품페이지 요청", "/api/v1/items/product"),
                                new Interface("IF002_SEARCH", "주문페이지 요청", "/api/v1/orders/request"),
                                new Interface("IF003_SEARCH", "회원정보 요청", "/api/v1/users/info"),
                                new Interface("IF004_SEARCH", "결제정보 요청", "/api/v1/payments/info"),
                                new Interface("IF005_SEARCH", "배송정보 요청", "/api/v1/shipping/info"),
                                new Interface("IF006_SEARCH", "리뷰정보 요청", "/api/v1/reviews/info"),
                                new Interface("IF007_SEARCH", "카테고리정보 요청", "/api/v1/categories/info"),
                                new Interface("IF008_SEARCH", "쿠폰정보 요청", "/api/v1/coupons/info"),
                                new Interface("IF009_SEARCH", "이벤트정보 요청", "/api/v1/events/info"),
                                new Interface("IF010_SEARCH", "공지사항 요청", "/api/v1/notices/info"),
                                new Interface("IF011_SEARCH", "FAQ 요청", "/api/v1/faqs/info"),
                                new Interface("IF012_SEARCH", "문의내역 요청", "/api/v1/inquiries/info"),
                                new Interface("IF013_SEARCH", "상품평 요청", "/api/v1/reviews/product"),
                                new Interface("IF014_SEARCH", "재고정보 요청", "/api/v1/inventory/info"),
                                new Interface("IF015_SEARCH", "환불정보 요청", "/api/v1/refunds/info"),
                                new Interface("IF016_SEARCH", "정산정보 요청", "/api/v1/settlements/info"),
                                new Interface("IF017_SEARCH", "포인트정보 요청", "/api/v1/points/info"),
                                if18,
                                if19,
                                if20);
                testDataList.forEach(interfaceRepository::save);
        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesForSearch.class)
        @DisplayName("1. 인터페이스_단건_조회")
        void 인터페이스_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */

                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
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

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesSearchAll.class)
        @DisplayName("2. 인터페이스_N건_조회")
        void 인터페이스_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {
                /* 1. given */

                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
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

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesForInesrt.class)
        @DisplayName("3. 인터페이스_추가")
        void 인터페이스_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);

                MvcResult actual = mockMvc.perform(
                                post(url)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .header("test-token", true)
                                                .params(multiValueMap))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesForUpdate.class)
        @DisplayName("4. 인터페이스_수정")
        void 인터페이스_수정(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);

                MvcResult actual = mockMvc.perform(
                                put(url)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .header("test-token", true)
                                                .params(multiValueMap))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

        @TestTemplate
        @ExtendWith(InterFaceTestCasesForDelete.class)
        @DisplayName("5. 인터페이스_삭제")
        void 인터페이스_삭제(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
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

                assertThat(expectedToJson).isEqualTo(actualToJson);

                interfaceRepository.findById(((InterfaceReqDto) testData).getIfId())
                                .ifPresent(actualSts -> assertThat(Status.NAGATIVE).isEqualTo(actualSts.getSts()));

        }

}
