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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.basic.app.api.ResponseApi;
import com.basic.app.entity.Company;
import com.basic.app.entity.Department;
import com.basic.app.featureTest.testcases.department.DepartmentTestCasesForDelete;
import com.basic.app.featureTest.testcases.department.DepartmentTestCasesForInesrt;
import com.basic.app.featureTest.testcases.department.DepartmentTestCasesForSearch;
import com.basic.app.featureTest.testcases.department.DepartmentTestCasesForUpdate;
import com.basic.app.featureTest.testcases.department.DepartmentTestCasesSearchAll;
import com.basic.app.repository.CompanyRepository;
import com.basic.app.repository.DepartmentRepository;
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
@Sql(scripts = {
                "classpath:sql/test-data/auth/auth-data.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {
                "classpath:sql/test-data/auth/cleanup-test-data.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class DepartmentTestTemplateTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private DepartmentRepository departmentRepository;

        @Autowired
        private CompanyRepository companyRepository;

        /*************************************
         * [테스트 데이터 세팅]
         *************************************/
        long startTime;

        private List<Department> testDataList = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트
        private List<Company> testDataCompanyList = new ArrayList<>(); // 테스트에 사용할 인터페이스 엔티티 리스트

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
                departmentRepository.deleteAllById(testDataList.stream()
                                .map(Department::getDeptCode)
                                .toList());

                companyRepository.deleteAllById(testDataCompanyList.stream()
                                .map(Company::getCompanyCode)
                                .toList());

        }

        public void initTestData() {

                testDataCompanyList = List.of(
                                new Company("C100_TEST", "Test Company", null),
                                new Company("C100_TEST2", "Test Company", null));

                companyRepository.saveAll(testDataCompanyList);

                Company company = companyRepository.findById("C100_TEST").orElse(null);

                testDataList = List.of(
                                new Department("20000000_TEST", "CBSK", "ROOT", 0, company, "Y", null),
                                new Department("21000000_TEST", "경영지원본부", "20000000_TEST", 1, company, "Y", null),
                                new Department("21100000_TEST", "인사팀", "21000000_TEST", 2, company, "Y", null),
                                new Department("21110000_TEST", "채용파트", "21100000_TEST", 3, company, "Y", null),
                                new Department("21111000_TEST", "신입채용팀", "21110000_TEST", 4, company, "Y", null),
                                new Department("21111100_TEST", "채용계획팀", "21111000_TEST", 5, company, "Y", null),
                                new Department("21112000_TEST", "경력채용팀", "21110000_TEST", 4, company, "Y", null),
                                new Department("21112100_TEST", "헤드헌팅팀", "21112000_TEST", 5, company, "Y", null),
                                new Department("21120000_TEST", "교육파트", "21100000_TEST", 3, company, "Y", null),
                                new Department("21121000_TEST", "직무교육팀", "21120000_TEST", 4, company, "Y", null),
                                new Department("21121100_TEST", "신입직무교육팀", "21121000_TEST", 5, company, "Y", null),
                                new Department("21122000_TEST", "리더십교육팀", "21120000_TEST", 4, company, "Y", null),
                                new Department("21200000_TEST", "재무회계팀", "21000000_TEST", 2, company, "Y", null),
                                new Department("21210000_TEST", "재무파트", "21200000_TEST", 3, company, "Y", null),
                                new Department("21211000_TEST", "자금관리팀", "21210000_TEST", 4, company, "Y", null),
                                new Department("21212000_TEST", "예산팀", "21210000_TEST", 4, company, "Y", null),
                                new Department("21212100_TEST", "예산기획팀", "21212000_TEST", 5, company, "Y", null),
                                new Department("21220000_TEST", "회계파트", "21200000_TEST", 3, company, "Y", null),
                                new Department("21221000_TEST", "결산팀", "21220000_TEST", 4, company, "Y", null),
                                new Department("21221100_TEST", "월별결산팀", "21221000_TEST", 5, company, "Y", null),
                                new Department("21222000_TEST", "세무팀", "21220000_TEST", 4, company, "Y", null),
                                new Department("21300000_TEST", "총무팀", "21000000_TEST", 2, company, "Y", null),
                                new Department("21310000_TEST", "법무파트", "21300000_TEST", 3, company, "Y", null),
                                new Department("21311000_TEST", "계약팀", "21310000_TEST", 4, company, "Y", null),
                                new Department("21312000_TEST", "특허팀", "21310000_TEST", 4, company, "Y", null),
                                new Department("21320000_TEST", "자산관리파트", "21300000_TEST", 3, company, "Y", null),
                                new Department("21321000_TEST", "시설관리팀", "21320000_TEST", 4, company, "Y", null),
                                new Department("22000000_TEST", "기술개발본부", "20000000_TEST", 1, company, "Y", null),
                                new Department("22100000_TEST", "플랫폼개발팀", "22000000_TEST", 2, company, "Y", null),
                                new Department("22110000_TEST", "백엔드파트", "22100000_TEST", 3, company, "Y", null),
                                new Department("22111000_TEST", "API개발팀", "22110000_TEST", 4, company, "Y", null),
                                new Department("22111100_TEST", "API개발1팀", "22111000_TEST", 5, company, "Y", null),
                                new Department("22111200_TEST", "API개발2팀", "22111000_TEST", 5, company, "Y", null),
                                new Department("22112000_TEST", "DB관리팀", "22110000_TEST", 4, company, "Y", null),
                                new Department("22112100_TEST", "DB운영팀", "22112000_TEST", 5, company, "Y", null),
                                new Department("22113000_TEST", "클라우드팀", "22110000_TEST", 4, company, "Y", null),
                                new Department("22120000_TEST", "프론트파트", "22100000_TEST", 3, company, "Y", null),
                                new Department("22121000_TEST", "웹개발팀", "22120000_TEST", 4, company, "Y", null),
                                new Department("22122000_TEST", "모바일개발팀", "22120000_TEST", 4, company, "Y", null),
                                new Department("22200000_TEST", "데이터분석팀", "22000000_TEST", 2, company, "Y", null),
                                new Department("22210000_TEST", "빅데이터파트", "22200000_TEST", 3, company, "Y", null),
                                new Department("22211000_TEST", "데이터수집팀", "22210000_TEST", 4, company, "Y", null),
                                new Department("22211100_TEST", "웹크롤링팀", "22211000_TEST", 5, company, "Y", null),
                                new Department("22212000_TEST", "데이터분석팀", "22210000_TEST", 4, company, "Y", null),
                                new Department("22220000_TEST", "AI연구파트", "22200000_TEST", 3, company, "Y", null),
                                new Department("22221000_TEST", "머신러닝팀", "22220000_TEST", 4, company, "Y", null),
                                new Department("22221100_TEST", "모델개발팀", "22221000_TEST", 5, company, "Y", null),
                                new Department("22222000_TEST", "자연어처리팀", "22220000_TEST", 4, company, "Y", null),
                                new Department("22222100_TEST", "텍스트분석팀", "22222000_TEST", 5, company, "Y", null),
                                new Department("22300000_TEST", "QA팀", "22000000_TEST", 2, company, "Y", null),
                                new Department("22310000_TEST", "수동테스트파트", "22300000_TEST", 3, company, "Y", null),
                                new Department("22311000_TEST", "기능테스트팀", "22310000_TEST", 4, company, "Y", null),
                                new Department("22320000_TEST", "자동화테스트파트", "22300000_TEST", 3, company, "Y", null),
                                new Department("22321000_TEST", "단위테스트팀", "22320000_TEST", 4, company, "Y", null),
                                new Department("23000000_TEST", "영업마케팅본부", "20000000_TEST", 1, company, "Y", null),
                                new Department("23100000_TEST", "국내영업팀", "23000000_TEST", 2, company, "Y", null),
                                new Department("23110000_TEST", "수도권영업파트", "23100000_TEST", 3, company, "Y", null),
                                new Department("23111000_TEST", "서울영업팀", "23110000_TEST", 4, company, "Y", null),
                                new Department("23111100_TEST", "강남영업팀", "23111000_TEST", 5, company, "Y", null),
                                new Department("23111200_TEST", "강북영업팀", "23111000_TEST", 5, company, "Y", null),
                                new Department("23112000_TEST", "경기영업팀", "23110000_TEST", 4, company, "Y", null),
                                new Department("23120000_TEST", "지방영업파트", "23100000_TEST", 3, company, "Y", null),
                                new Department("23121000_TEST", "충청영업팀", "23120000_TEST", 4, company, "Y", null),
                                new Department("23121100_TEST", "대전영업팀", "23121000_TEST", 5, company, "Y", null),
                                new Department("23122000_TEST", "경상영업팀", "23120000_TEST", 4, company, "Y", null),
                                new Department("23123000_TEST", "전라영업팀", "23120000_TEST", 4, company, "Y", null),
                                new Department("23200000_TEST", "B2B영업팀", "23000000_TEST", 2, company, "Y", null),
                                new Department("23210000_TEST", "솔루션영업파트", "23200000_TEST", 3, company, "Y", null),
                                new Department("23211000_TEST", "금융솔루션팀", "23210000_TEST", 4, company, "Y", null),
                                new Department("23211100_TEST", "금융솔루션1팀", "23211000_TEST", 5, company, "Y", null),
                                new Department("23212000_TEST", "제조솔루션팀", "23210000_TEST", 4, company, "Y", null),
                                new Department("23220000_TEST", "중소기업영업파트", "23200000_TEST", 3, company, "Y", null),
                                new Department("23221000_TEST", "대기업영업팀", "23220000_TEST", 4, company, "Y", null),
                                new Department("23222000_TEST", "중소기업영업팀", "23220000_TEST", 4, company, "Y", null),
                                new Department("23222100_TEST", "중소기업영업1팀", "23222000_TEST", 5, company, "Y", null),
                                new Department("23300000_TEST", "온라인마케팅팀", "23000000_TEST", 2, company, "Y", null),
                                new Department("23310000_TEST", "검색광고파트", "23300000_TEST", 3, company, "Y", null),
                                new Department("23311000_TEST", "네이버광고팀", "23310000_TEST", 4, company, "Y", null),
                                new Department("23311100_TEST", "네이버광고1팀", "23311000_TEST", 5, company, "Y", null),
                                new Department("23312000_TEST", "구글광고팀", "23310000_TEST", 4, company, "Y", null),
                                new Department("23320000_TEST", "SNS광고파트", "23300000_TEST", 3, company, "Y", null),
                                new Department("23321000_TEST", "인스타그램팀", "23320000_TEST", 4, company, "Y", null),
                                new Department("23322000_TEST", "페이스북팀", "23320000_TEST", 4, company, "Y", null),
                                new Department("23400000_TEST", "홍보팀", "23000000_TEST", 2, company, "Y", null),
                                new Department("23410000_TEST", "언론홍보파트", "23400000_TEST", 3, company, "Y", null),
                                new Department("23411000_TEST", "보도자료팀", "23410000_TEST", 4, company, "Y", null),
                                new Department("23411100_TEST", "보도자료1팀", "23411000_TEST", 5, company, "Y", null),
                                new Department("23412000_TEST", "미디어팀", "23410000_TEST", 4, company, "Y", null),
                                new Department("23420000_TEST", "대외협력파트", "23400000_TEST", 3, company, "Y", null),
                                new Department("24000000_TEST", "해외사업본부", "20000000_TEST", 1, company, "Y", null),
                                new Department("24100000_TEST", "미주사업팀", "24000000_TEST", 2, company, "Y", null),
                                new Department("24110000_TEST", "북미사업파트", "24100000_TEST", 3, company, "Y", null),
                                new Department("24111000_TEST", "캐나다사업팀", "24110000_TEST", 4, company, "Y", null),
                                new Department("24111100_TEST", "캐나다동부팀", "24111000_TEST", 5, company, "Y", null),
                                new Department("24112000_TEST", "멕시코사업팀", "24110000_TEST", 4, company, "Y", null),
                                new Department("24120000_TEST", "남미사업파트", "24100000_TEST", 3, company, "Y", null),
                                new Department("24121000_TEST", "브라질사업팀", "24120000_TEST", 4, company, "Y", null),
                                new Department("24122000_TEST", "칠레사업팀", "24120000_TEST", 4, company, "Y", null),
                                new Department("24200000_TEST", "유럽사업팀", "24000000_TEST", 2, company, "Y", null),
                                new Department("24210000_TEST", "서유럽파트", "24200000_TEST", 3, company, "Y", null),
                                new Department("24211000_TEST", "영국사업팀", "24210000_TEST", 4, company, "Y", null),
                                new Department("24211100_TEST", "런던사업팀", "24211000_TEST", 5, company, "Y", null),
                                new Department("24212000_TEST", "프랑스사업팀", "24210000_TEST", 4, company, "Y", null),
                                new Department("24213000_TEST", "독일사업팀", "24210000_TEST", 4, company, "Y", null),
                                new Department("24220000_TEST", "동유럽파트", "24200000_TEST", 3, company, "Y", null),
                                new Department("24221000_TEST", "폴란드사업팀", "24220000_TEST", 4, company, "Y", null),
                                new Department("24300000_TEST", "아시아사업팀", "24000000_TEST", 2, company, "Y", null),
                                new Department("24310000_TEST", "동남아파트", "24300000_TEST", 3, company, "Y", null),
                                new Department("24311000_TEST", "베트남사업팀", "24310000_TEST", 4, company, "Y", null),
                                new Department("24312000_TEST", "태국사업팀", "24310000_TEST", 4, company, "Y", null),
                                new Department("24313000_TEST", "필리핀사업팀", "24310000_TEST", 4, company, "Y", null),
                                new Department("24314000_TEST", "인도네시아사업팀", "24310000_TEST", 4, company, "Y", null),
                                new Department("24320000_TEST", "서아시아파트", "24300000_TEST", 3, company, "Y", null),
                                new Department("24321000_TEST", "인도사업팀", "24320000_TEST", 4, company, "Y", null));
                // testDataList.forEach(departmentRepository::save);
                departmentRepository.saveAll(testDataList);

        }

        @TestTemplate
        @ExtendWith(DepartmentTestCasesForSearch.class)
        @DisplayName("1. 부서_단건_조회")
        void 부서_단건_조회(TestCaseDetail<?> testCaseDetail) throws Exception {

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
        @ExtendWith(DepartmentTestCasesSearchAll.class)
        @DisplayName("2. 부서_N건_조회")
        void 부서_N건_조회(TestCaseDetailSearchForm<?, ?> testCaseDetail) throws Exception {
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
        @ExtendWith(DepartmentTestCasesForInesrt.class)
        @DisplayName("3. 부서_추가")
        void 부서_추가_미사용(TestCaseDetail<?> testCaseDetail) throws Exception {

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
        @ExtendWith(DepartmentTestCasesForUpdate.class)
        @DisplayName("4. 부서_수정")
        void 부서_수정_미사용(TestCaseDetail<?> testCaseDetail) throws Exception {

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
        @ExtendWith(DepartmentTestCasesForDelete.class)
        @DisplayName("5. 부서_삭제")
        void 부서_삭제_미사용(TestCaseDetail<?> testCaseDetail) throws Exception {

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
