package com.basic.app.FeatureTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import com.basic.app.exception.ErrorCode;
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
public class InterfaceCrudTest {

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

        // 예시 기본 정상 데이터
        private Map<String, Object> baseTestData() {
                return Map.of(
                                "ifId", "IF100",
                                "ifName", "기본 테스트 인터페이스",
                                "status", "ACTIVE",
                                "createdBy", "admin");
        }

        /**
         * 테스트에 사용할 사용자 데이터를 제공하는 메서드
         * 각 User 인스턴스가 하나의 테스트 케이스가 됨
         */
        static Stream<InterfaceReqDto> interfaceInitData() {
                return Stream.of(
                                new InterfaceReqDto("IF001", "회원가입 요청", "/api/v1/users/signup"),
                                new InterfaceReqDto("IF002", "로그인 요청", "/api/v1/users/login"),
                                new InterfaceReqDto("IF030", "세션 체크", "/api/v1/auth/session/check"));

        }

        Stream<Arguments> 인터페이스_기준정보_조회_TestData() {
                // [TC_ID : TC-097] [TC명 : 인터페이스 기준정보 조회1] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
                // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : 정상 조회 1건 조회]
                String testName1 = "정상조회";
                InterfaceReqDto testData1 = new InterfaceReqDto("IF002_SEARCH", "상품페이지 요청", "/api/v1/items/product");
                ApiResponse<?> expected1 = ApiResponse.success(Map.of("data", testData1));
                ResultMatcher status1 = status().isOk();
                String url1 = BASE_URL + "/" + testData1.getIfId();

                InterfaceReqDto searchForm1 = new InterfaceReqDto("IF002_SEARCH", "상품페이지 요청", "/api/v1/items/product");
                boolean preSave1 = true; // 사전에 먼저 저장이 필요한 테스트 케이스

                // [TC_ID : TC-098] [TC명 : 인터페이스 기준정보 조회2] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
                // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 필수값 누락] [테스트 상세 : N/A]
                // 테스트 케이스 미존재

                // [TC_ID : TC-099] [TC명 : 인터페이스 기준정보 조회3] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
                // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 존재하지 않는 ID 조회] [테스트 상세 : 존재하지
                // 않는ID 조회]
                String testName3 = "존재하지 않는 ID 조회";
                InterfaceReqDto testData3 = new InterfaceReqDto("IF_ID_NOT_EXIST", "상품페이지 요청", "/api/v1/items/product");
                ApiResponse<?> expected3 = ApiResponse.fail(ErrorCode.OBJECT_NOT_FOUND);
                ResultMatcher status3 = status().is4xxClientError();
                String url3 = BASE_URL + "/" + testData3.getIfId();

                InterfaceReqDto searchForm3 = null;
                boolean preSave3 = false; // 사전에 먼저 저장이 필요한 테스트 케이스

                // [TC_ID : TC-100] [TC명 : 인터페이스 기준정보 조회4] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
                // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 비즈니스로직 케이스] [테스트 상세 : N/A]
                // 테스트 케이스 미존재

                // [TC_ID : TC-101] [TC명 : 인터페이스 기준정보 조회5] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 >
                // 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : 잘못된 형식 입력] [테스트 상세 : 잘못된 형식 입력]
                String testName5 = "잘못된 형식 입력";
                InterfaceReqDto testData5 = null; // 잘못된 형식 입력을 위한 DTO는 null로 설정
                ApiResponse<?> expected5 = ApiResponse.fail(ErrorCode.PAGE_NOT_FOUND);
                String url5 = BASE_URL + "/";
                ResultMatcher status5 = status().is4xxClientError();

                InterfaceReqDto searchForm5 = null;
                boolean preSave5 = false; // 사전에 먼저 저장이 필요한 테스트 케이스

                if (testData1 != null)
                        afterAllDeleteList.add(testData1.getIfId());
                if (testData3 != null)
                        afterAllDeleteList.add(testData3.getIfId());
                if (testData5 != null)
                        afterAllDeleteList.add(testData5.getIfId());

                return Stream.of(
                                Arguments.of(new TestCaseDetailForSearch<>(url1, testName1, testData1, expected1,
                                                status1, searchForm1, preSave1)),
                                Arguments.of(new TestCaseDetailForSearch<>(url3, testName3, testData3, expected3,
                                                status3, searchForm3, preSave3)),
                                Arguments.of(new TestCaseDetailForSearch<>(url5, testName5, testData5, expected5,
                                                status5, searchForm5, preSave5)));
        }

        @ParameterizedTest
        @MethodSource("인터페이스_기준정보_조회_TestData")
        @DisplayName("[TC_ID : TC-097] [TC명 : 인터페이스 기준정보 조회1] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]")
        void 인터페이스_기준정보_조회(TestCaseDetailForSearch<?, ?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                boolean preSave = testCaseDetail.isPreSave(); // 사전에 먼저 저장이 필요한 테스트 케이스

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                if (preSave) { // 잘못된 형식 테스트에는 테스트 데이터가 없음
                        MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);
                        mockMvc.perform(
                                        post(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                        .params(multiValueMap))
                                        .andExpect(status().isOk())
                                        .andReturn();
                }

                // 조회
                MvcResult actual = mockMvc.perform(get(url))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

        Stream<Arguments> 인터페이스_추가_TestData() {

                /*
                 * [TC_ID : TC-112] [TC명 : 인터페이스 추가1] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
                 * 관리] [기능 : 인터페이스 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
                 */
                String url1 = BASE_URL;
                String testName1 = "정상등록";
                InterfaceReqDto testData1 = new InterfaceReqDto("IF001", "회원가입 요청", "/api/v1/users/signup");
                ApiResponse<?> expected1 = ApiResponse.success(Map.of("data", testData1));
                ResultMatcher status1 = status().isOk();

                /*
                 * [TC_ID : TC-113] [TC명 : 인터페이스 추가2] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
                 * 관리] [기능 : 인터페이스 추가] [테스트항목 : 필수값 누락]
                 */
                String url2 = BASE_URL;
                String testName2 = "필수값 누락(IF ID)";
                InterfaceReqDto testData2 = new InterfaceReqDto("", "회원가입 요청", "/api/v1/users/signup");
                ApiResponse<?> expected2 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "IF아이디는 필수입니다.");
                ResultMatcher status2 = status().is4xxClientError();

                /*
                 * [TC_ID : TC-113] [TC명 : 인터페이스 추가2] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
                 * 관리] [기능 : 인터페이스 추가] [테스트항목 : 필수값 누락]
                 */
                String url3 = BASE_URL;
                String testName3 = "필수값 누락(IF명)";
                InterfaceReqDto testData3 = new InterfaceReqDto("IF001", "", "/api/v1/users/signup");
                ApiResponse<?> expected3 = ApiResponse.fail(ErrorCode.VALIDATION_ERROR_CLIENT, "인터페이스명은 필수입니다.");
                ResultMatcher status3 = status().is4xxClientError();
                /*
                 * [TC_ID : TC-112] [TC명 : 인터페이스 추가1] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
                 * 관리] [기능 : 인터페이스 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
                 */

                String url4 = BASE_URL;
                String testName4 = "존재하는 ID 등록 시도(저장)";
                InterfaceReqDto testData4 = new InterfaceReqDto("IF002", "회원가입 요청", "/api/v1/users/signup");
                ApiResponse<?> expected4 = ApiResponse.success(Map.of("data", testData4));
                ResultMatcher status4 = status().isOk();

                /*
                 * [TC_ID : TC-112] [TC명 : 인터페이스 추가1] [REQ_ID : REQ_ADM_024] [화면 : 기준 정보 > 인터페이스
                 * 관리] [기능 : 인터페이스 추가] [테스트항목 : 정상 등록] [테스트 상세 : ]
                 */

                String url5 = BASE_URL;
                String testName5 = "존재하는 ID 등록 시도(중복)";
                InterfaceReqDto testData5 = new InterfaceReqDto("IF002", "상품페이지 요청", "/api/v1/items/product");
                ApiResponse<?> expected5 = ApiResponse.fail(ErrorCode.OBJECT_IS_EXISTED);
                ResultMatcher status5 = status().is4xxClientError();

                afterAllDeleteList.add(testData1.getIfId());
                afterAllDeleteList.add(testData2.getIfId());
                afterAllDeleteList.add(testData3.getIfId());
                afterAllDeleteList.add(testData4.getIfId());
                afterAllDeleteList.add(testData5.getIfId());

                return Stream.of(
                                Arguments.of(new TestCaseDetail<>(url1, testName1, testData1, expected1, status1)),
                                Arguments.of(new TestCaseDetail<>(url2, testName2, testData2, expected2, status2)),
                                Arguments.of(new TestCaseDetail<>(url3, testName3, testData3, expected3, status3)),
                                Arguments.of(new TestCaseDetail<>(url4, testName4, testData4, expected4, status4)),
                                Arguments.of(new TestCaseDetail<>(url5, testName5, testData5, expected5, status5)));
        }

        @ParameterizedTest
        @MethodSource("인터페이스_추가_TestData")
        @DisplayName("인터페이스_추가")
        void 인터페이스_추가(TestCaseDetail<?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);

                /* 2. when */

                TestUtils.showLogTestCaseStart(testCaseName);

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
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //

        @Test
        @DisplayName("[인터페이스 추가] 필수값 누락")
        void testCreateInterface_MissingRequiredField() throws Exception {
                Map<String, Object> data = Map.of(
                                "ifName", "이름만 있음"
                // ifId 누락
                );

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[인터페이스 추가] 존재하는 ID 등록 시도")
        void testCreateInterface_DuplicateId() throws Exception {
                Map<String, Object> data = baseTestData();

                // 1차 등록
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                // 중복 등록 시도
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isConflict()); // 409 예상
        }

        @Test
        @DisplayName("[인터페이스 추가] 비즈니스 로직 검증 실패")
        void testCreateInterface_BusinessLogicFailure() throws Exception {
                Map<String, Object> data = Map.of(
                                "ifId", "IF_BL_FAIL",
                                "ifName", "비즈니스 로직 실패 테스트",
                                "status", "INVALID_STATUS", // 잘못된 상태값
                                "createdBy", "admin");

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[인터페이스 추가] 잘못된 형식 입력")
        void testCreateInterface_InvalidFormat() throws Exception {
                String invalidJson = "{ invalid json }";

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[인터페이스 수정] 정상 수정")
        void testUpdateInterface_Success() throws Exception {
                Map<String, Object> data = baseTestData();

                // 등록
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                // 수정
                Map<String, Object> updatedData = Map.of(
                                "ifId", data.get("ifId"),
                                "ifName", "수정된 이름",
                                "status", "INACTIVE",
                                "createdBy", "editor");

                mockMvc.perform(put(BASE_URL + "/" + data.get("ifId"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(updatedData)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.ifName").value("수정된 이름"));
        }

        @Test
        @DisplayName("[인터페이스 수정] 필수값 누락")
        void testUpdateInterface_MissingRequiredField() throws Exception {
                Map<String, Object> data = baseTestData();

                // 등록
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                // 수정 요청에 필수값 누락
                Map<String, Object> incompleteData = Map.of(
                                "ifName", "이름만 있음");

                mockMvc.perform(put(BASE_URL + "/" + data.get("ifId"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(incompleteData)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[인터페이스 수정] 존재하지 않는 ID 수정 시도")
        void testUpdateInterface_NotFound() throws Exception {
                Map<String, Object> updatedData = Map.of(
                                "ifId", "NOT_EXIST_ID",
                                "ifName", "수정 시도",
                                "status", "ACTIVE",
                                "createdBy", "admin");

                mockMvc.perform(put(BASE_URL + "/NOT_EXIST_ID")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(updatedData)))
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("[인터페이스 수정] 비즈니스 로직 실패")
        void testUpdateInterface_BusinessLogicFailure() throws Exception {
                Map<String, Object> data = baseTestData();

                // 등록
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                // 상태값 비즈니스 규칙 위반 수정 시도
                Map<String, Object> invalidUpdate = Map.of(
                                "ifId", data.get("ifId"),
                                "ifName", "업데이트 실패 테스트",
                                "status", "INVALID_STATUS",
                                "createdBy", "admin");

                mockMvc.perform(put(BASE_URL + "/" + data.get("ifId"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(invalidUpdate)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[인터페이스 수정] 잘못된 형식 입력")
        void testUpdateInterface_InvalidFormat() throws Exception {
                String invalidJson = "{ invalid json }";

                mockMvc.perform(put(BASE_URL + "/IF100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[인터페이스 삭제] 정상 삭제")
        void testDeleteInterface_Success() throws Exception {
                Map<String, Object> data = baseTestData();

                // 등록
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                // 삭제
                mockMvc.perform(delete(BASE_URL + "/" + data.get("ifId")))
                                .andExpect(status().isNoContent());

                // 삭제 후 조회 404
                mockMvc.perform(get(BASE_URL + "/" + data.get("ifId")))
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("[인터페이스 삭제] 이미 삭제한 항목 삭제 시도")
        void testDeleteInterface_AlreadyDeleted() throws Exception {
                Map<String, Object> data = baseTestData();

                // 등록 및 삭제
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                mockMvc.perform(delete(BASE_URL + "/" + data.get("ifId")))
                                .andExpect(status().isNoContent());

                // 재삭제 시도
                mockMvc.perform(delete(BASE_URL + "/" + data.get("ifId")))
                                .andExpect(status().isNotFound()); // 이미 없는 리소스라 404 가정
        }

        @Test
        @DisplayName("[인터페이스 삭제] 존재하지 않는 ID 삭제 시도")
        void testDeleteInterface_NotFound() throws Exception {
                mockMvc.perform(delete(BASE_URL + "/NON_EXIST_ID"))
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("[인터페이스 삭제] 비즈니스 로직 실패 케이스")
        void testDeleteInterface_BusinessLogicFailure() throws Exception {
                String ifId = "IF_NO_DELETE";

                // 예: 특정 조건에 의해 삭제 불가한 항목 등록 (상태값 등)
                Map<String, Object> data = Map.of(
                                "ifId", ifId,
                                "ifName", "삭제 불가 항목",
                                "status", "LOCKED",
                                "createdBy", "admin");

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(data)))
                                .andExpect(status().isCreated());

                // 삭제 시 403 Forbidden 등
                mockMvc.perform(delete(BASE_URL + "/" + ifId))
                                .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("[인터페이스 삭제] 잘못된 형식 입력")
        void testDeleteInterface_InvalidFormat() throws Exception {
                mockMvc.perform(delete(BASE_URL + "/@@@!!!"))
                                .andExpect(status().isBadRequest());
        }

        // 이 메서드는 여러 번 실행됨
        @TestTemplate
        @ExtendWith(SearchTestInvocationProvider.class)
        void 검색_테스트(String keyword, int page, int size) {
                System.out.printf("🔍 검색어: %s, 페이지: %d, 크기: %d%n", keyword, page, size);

                // 테스트 로직 작성: 예) 서비스 호출 후 결과 검증
                // searchService.search(keyword, PageRequest.of(page, size));
        }

        @TestTemplate
        @ExtendWith(SearchTestInvocationProvider.class)
        @DisplayName("[TC_ID : TC-097] [TC명 : 인터페이스 기준정보 조회1] [REQ_ID : REQ_ADM_021] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] [테스트항목 : [단건] 정상 조회] [테스트 상세 : ]")
        void 인터페이스_기준정보_조회2(TestCaseDetailForSearch<?, ?> testCaseDetail) throws Exception {

                /* 1. given */
                String url = testCaseDetail.getUrl();
                String testCaseName = testCaseDetail.getTestName();
                Object testData = testCaseDetail.getTestData();
                ApiResponse<?> expected = testCaseDetail.getExpected();
                ResultMatcher httpStatus = testCaseDetail.getHttpStatus();

                boolean preSave = testCaseDetail.isPreSave(); // 사전에 먼저 저장이 필요한 테스트 케이스

                /* 2. when */
                TestUtils.showLogTestCaseStart(testCaseName);

                if (preSave) { // 잘못된 형식 테스트에는 테스트 데이터가 없음
                        MultiValueMap<String, String> multiValueMap = TestUtils.dtoToMultiValueMap(testData);
                        mockMvc.perform(
                                        post(BASE_URL)
                                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                        .params(multiValueMap))
                                        .andExpect(status().isOk())
                                        .andReturn();
                }

                // 조회
                MvcResult actual = mockMvc.perform(get(url))
                                .andExpect(httpStatus)
                                .andReturn();

                /* 3. then */
                JsonNode expectedToJson = TestUtils.apiReponseToJsonNode(expected);
                JsonNode actualToJson = TestUtils.mvcResultToJsonNode(actual);

                TestUtils.showLogTestCaseEnd(testData, expectedToJson, actualToJson);

                assertThat(expectedToJson).isEqualTo(actualToJson);

        }

}
